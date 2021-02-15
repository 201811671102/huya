package com.cs.heart_release_sock.handler;

import com.cs.heart_release_sock.HeartReleaseSockApplication;
import com.cs.heart_release_sock.base.utils.ChannelUtil;
import com.cs.heart_release_sock.manager.ConnManager;
import com.cs.heart_release_sock.pojo.MessageRecord;
import com.cs.heart_release_sock.protocol.Packet;
import com.cs.heart_release_sock.protocol.Protocol;
import com.cs.heart_release_sock.service.MessageRecordService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description websocket协议处理器
 *
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    //解决@Autowired为null
    private static WebsocketHandler websocketHandler;
    @PostConstruct
    public void init(){
        websocketHandler = this;
    }
    @Autowired
    MessageRecordService messageRecordService;


    private final ExecutorService bizThreadPool = HeartReleaseSockApplication.executorService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame data = (BinaryWebSocketFrame) msg;
            handleWSBinaryFrame(ctx, data);
        } else if (msg instanceof CloseWebSocketFrame) {
            handleWSClose(ctx);
        } else if(msg instanceof TextWebSocketFrame) {
            handleWSClose(ctx);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent && ((IdleStateEvent)evt).state() == IdleState.READER_IDLE) {
            handleWSClose(ctx);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    protected void handleWSBinaryFrame(ChannelHandlerContext ctx,BinaryWebSocketFrame data) throws IOException, ClassNotFoundException {
        ByteBuf byteBuf = data.content();
        Packet packet = Packet.parseFrom(ByteBufUtil.getBytes(byteBuf));
        if(packet != null){
            bizThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        // 拿到协议号
                        Protocol protocol = Protocol.forNumber(packet.getCode());
                        Integer uid = ConnManager.getInstance().getUid(ctx.channel());
                        Object body = packet.getBody();
                        if(protocol == null){
                            handleWSClose(ctx.channel());
                            return;
                        }
                        boolean alive = ConnManager.getInstance().isAlive(packet.getTo());
                        MessageRecord messageRecord = null;
                        // 根据不同协议，进入部分逻辑分支处理
                        switch (protocol) {
                            // 客户端心跳请求
                            case Heartbeat:
                                ChannelUtil.write(ctx,Packet.newBuilder()
                                    .setCode(Protocol.Heartbeat_VALUE)
                                    .setFrom(0)
                                    .setTo(uid)
                                    .build());
                                break;
                            //点赞
                            case User_Thumb_Up:
                            //评论
                            case User_Comment:
                            //访客
                            case User_Access:
                            //咨询
                            case User_Consulting:
                            //回复咨询
                            case User_Consulting_Consult:
                            //收藏
                            case User_Collection:
                                messageRecord = new MessageRecord(packet.getCode(),packet.getFrom(),packet.getTo(),new Date(),alive,packet.toByteArray());
                                websocketHandler.messageRecordService.insert(messageRecord);
                                ChannelUtil.write(ConnManager.getInstance().getChannel(packet.getTo()),packet);
                                break;
                            //发送位置
                            case User_Position:
                            //交流
                            case User_Communication:
                            //发送图片
                            case User_Communication_photo:
                            //通话
                            case User_Call:
                                if (!alive){
                                    noOnLine(ctx,packet);
                                }
                                messageRecord = new MessageRecord(packet.getCode(),packet.getFrom(),packet.getTo(),new Date(),alive,packet.toByteArray());
                                websocketHandler.messageRecordService.insert(messageRecord);
                                ChannelUtil.write(ConnManager.getInstance().getChannel(packet.getTo()),packet);
                                break;
                            default:
                                // 未知协议
                                handleWSClose(ctx);
                                break;
                        }
                    }catch (Exception e){
                        log.error("handle frame failed.", e);
                    }
                }
            });
        }
    }

    public void noOnLine(ChannelHandlerContext ctx,Packet packet){
        ChannelUtil.write(ctx, Packet
                .newBuilder()
                .setCode(Protocol.User_No_Online_VALUE)
                .setFrom(0)
                .setTo(packet.getTo())
                .build());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        handleWSClose(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnManager.getInstance().onLogout(ctx.channel());
    }

    public void handleWSClose(ChannelHandlerContext ctx) {
        handleWSClose(ctx.channel());
    }

    public void handleWSClose(Channel channel) {
        channel.writeAndFlush(new CloseWebSocketFrame());
        channel.close();
    }


}
