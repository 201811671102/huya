package pre.cg.ws.websocket.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Preconditions;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import pre.cg.ws.base.Packet;
import pre.cg.ws.base.Protocol;
import pre.cg.ws.base.WSConfig;
import pre.cg.ws.websocket.ChannelUtil;
import pre.cg.ws.websocket.ConnManager;
import pre.cg.ws.websocket.RoomManager;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.UNAUTHORIZED;

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
                    log.info("收到新连接->uid:{}, profileId:{}, isPresenter:{}", userId, profileId, userId.equals(profileId));
                    handleHandshake(ctx, request, userId, roomId, profileId);
                }catch (JWTVerificationException e){
                    log.error("token校验失败 jwt:"+jwt, e);
                    ctx.close();
                }
            }else{
                // 普通http请求
                FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, UNAUTHORIZED, Unpooled.wrappedBuffer("非websocket请求".getBytes()));
                HttpUtil.setContentLength(res, res.content().readableBytes());
                ctx.channel().writeAndFlush(res);
            }
        }
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
                        if (!RoomManager.getInstance().isRoom(roomId)){
                            if (uid.equals(profileId)){
                                ConnManager.getInstance().onLogin(uid, roomId, ctx.channel(),profileId);
                                RoomManager.getInstance().openRoom(roomId,profileId);
                            }else{
                                Packet packet = new Packet(Protocol.Room_Close.getValue(),"");
                                ChannelUtil.write(ctx.channel(),Packet.forSend(packet));
 //                               ctx.close();
                            }
                        }else{
                            // 加入房间
                            ConnManager.getInstance().onLogin(uid, roomId, ctx.channel(),profileId);
                            RoomManager.getInstance().joinRoom(roomId,uid);
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
