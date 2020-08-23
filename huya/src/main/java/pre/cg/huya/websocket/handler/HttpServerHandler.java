package pre.cg.huya.websocket.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Preconditions;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import pre.cg.huya.base.websocket.*;
import pre.cg.huya.controller.RecordController;
import pre.cg.huya.websocket.manage.ConnManager;
import pre.cg.huya.websocket.manage.PlayInfoManager;
import pre.cg.huya.websocket.manage.RoomManager;


import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description http协议处理
 */
@Slf4j
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private JWTVerifier verifier;


    private static final WebsocketHandler websocketHandler = new WebsocketHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            HttpHeaders headers = request.headers();
            // 判断ws upgrade
            if (!StringUtils.isEmpty(headers.get(HttpHeaderNames.SEC_WEBSOCKET_KEY))) {
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
                Map<String, List<String>> params = queryStringDecoder.parameters();
                Preconditions.checkArgument(params.containsKey("jwt"), "jwt参数为空");
                String jwt = params.get("jwt").get(0);
                try {
                    // jwt校验
                    Algorithm algorithm = Algorithm.HMAC256(WSConfig.secretKey);
                    this.verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(jwt);
                    String userId = decodedJWT.getClaim("userId").asString();
                    String roomId = decodedJWT.getClaim("roomId").asString();
                    String profileId = decodedJWT.getClaim("profileId").asString();
                    Preconditions.checkArgument(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(roomId) && !StringUtils.isEmpty(profileId),
                            "token信息错误，userId:{}, roomId:{}, profileId:{}", userId, roomId, profileId);
                    // ws握手
                    log.info("收到新连接->uid:{}, roomid:{},profileId:{}, isPresenter:{}", userId, roomId,profileId, userId.equals(profileId));
                    handleHandshake(ctx, request, userId, roomId, profileId);
                }catch (JWTVerificationException e){
                    log.error("token校验失败 jwt:"+jwt, e);
                    ctx.close();
                }
            }else{
                FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(new RecordController().getAll().toString().getBytes()));
                HttpUtil.setContentLength(res, res.content().readableBytes());
                ctx.channel().writeAndFlush(res);
            }
        }
        ctx.flush();
    }

    private void handleHandshake(
            final ChannelHandlerContext ctx, HttpRequest req, String uid, String roomId, String profileId){
        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory(getWebSocketURL(req), null, true);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            ChannelFuture future = handshaker.handshake(ctx.channel(), req);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        // 握手成功，升级到websocket协议
                        ctx.pipeline().replace(ctx.handler(), "websocketHandler", websocketHandler);
                        ConnManager.getInstance().onLogin(uid, roomId, ctx.channel(),profileId);
                        if (!RoomManager.getInstance().isRoom(roomId)){
                             if (uid.equals(profileId)){
                                RoomManager.getInstance().inProfileRoom(roomId,profileId);
                            }else{
                                RoomManager.getInstance().joinWaitRoom(roomId,uid);
                                MessageUtil.unicast(uid,Protocol.Room_Close.getValue(),"",roomId);
                            }
                        }else{
                            // 加入房间
                            PlayInfo playInfo = new PlayInfo();
                            playInfo.setUid(uid);
                            RoomManager.getInstance().joinRoom(roomId, playInfo);
                            MessageUtil.broadcast(Protocol.Room_Open.getValue(), "", roomId);
                        }
                    }else{
                        // 握手失败
                        ctx.close();
                    }
                }
            });
        }
    }

    private String getWebSocketURL(HttpRequest req) {
        return "ws://" + req.headers().get(HttpHeaderNames.HOST) + req.getUri();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("http channel exception. close connection..." + " " + ctx.channel().id(), cause);
        ctx.close();
    }

}
