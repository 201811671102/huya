package com.cs.heart_release_sock.handler;

import com.cs.heart_release_sock.HeartReleaseSockApplication;
import com.cs.heart_release_sock.base.utils.ChannelUtil;
import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.manager.ConnManager;
import com.cs.heart_release_sock.pojo.MessageRecord;
import com.cs.heart_release_sock.protocol.Message;
import com.cs.heart_release_sock.protocol.Packet;
import com.cs.heart_release_sock.protocol.Protocol;
import com.cs.heart_release_sock.service.MessageRecordService;
import com.google.common.base.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.Timestamps;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description http协议处理
 */
@Slf4j
@Component
public class HttpServerHandler extends ChannelInboundHandlerAdapter {



    public static HttpServerHandler httpServerHandler;
    @PostConstruct
    public void init(){httpServerHandler = this;}
    @Autowired
    MessageRecordService messageRecordService;

    private final ExecutorService bizThreadPool = HeartReleaseSockApplication.executorService;



    private static final WebsocketHandler websocketHandler = new WebsocketHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof FullHttpRequest) {
                HttpRequest request = (HttpRequest) msg;
                HttpHeaders headers = request.headers();
                // 判断ws upgrade
                if (!StringUtils.isEmpty(headers.get(HttpHeaderNames.SEC_WEBSOCKET_KEY))) {
                    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
                    Map<String, List<String>> params = queryStringDecoder.parameters();
                    Preconditions.checkArgument(params.containsKey("uid"), "uid参数为空");
                    Integer uid = Integer.parseInt(params.get("uid").get(0));
                    try {
                        handleHandshake(ctx, request, uid);
                    }catch (Exception e){
                        ctx.close();
                    }
                }else{
                    FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST, Unpooled.wrappedBuffer(new String("没有权限").getBytes()));
                    HttpUtil.setContentLength(res, res.content().readableBytes());
                    ctx.channel().writeAndFlush(res);
                }
            }
        }catch (Exception e){
            ctx.close();
            ReferenceCountUtil.release(msg);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handleHandshake(
            final ChannelHandlerContext ctx, HttpRequest req, Integer uid){
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
                        ConnManager.getInstance().onLogin(uid,ctx.channel());
                        List<MessageRecord> messageRecords = httpServerHandler.messageRecordService.selectByTo(uid);
                        if (!messageRecords.isEmpty()){
                            bizThreadPool.submit(new Runnable() {
                                @Override
                                public void run() {
                                    messageRecords.forEach(messageRecord -> {
                                        Packet packet = null;
                                        try {
                                            //发送消息
                                            packet = Packet.parseFrom(messageRecord.getContent());
                                            ChannelUtil.write(ctx,packet);
                                            //更新数据库
                                            httpServerHandler.messageRecordService.update(messageRecord);
                                        }catch (Exception e) {
                                            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"更新消息记录出错！"+'\n'+e.getMessage());
                                        }
                                    });
                                }
                            });
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
