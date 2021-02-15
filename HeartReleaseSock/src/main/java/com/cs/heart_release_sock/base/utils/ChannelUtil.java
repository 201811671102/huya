package com.cs.heart_release_sock.base.utils;

import com.cs.heart_release_sock.manager.ConnManager;
import com.cs.heart_release_sock.pojo.MessageRecord;
import com.cs.heart_release_sock.protocol.Packet;
import com.cs.heart_release_sock.protocol.Protocol;
import com.cs.heart_release_sock.service.MessageRecordService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description 服务端回包工具类，封装回包逻辑
 */
@Slf4j
@Configuration
public class ChannelUtil {

    public static void write(ChannelHandlerContext ctx, Packet packet) {
        ByteBuf buf = ctx.alloc().directBuffer();
        BinaryWebSocketFrame resp = new BinaryWebSocketFrame(buf.writeBytes(packet.toByteArray()));
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(resp);
            }
        });
    }

    public static void write(Channel channel, Packet packet) {
        if(channel != null && channel.isWritable()) {
            ByteBuf buf = channel.alloc().directBuffer();
            BinaryWebSocketFrame resp = new BinaryWebSocketFrame(buf.writeBytes(packet.toByteArray()));
            channel.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    channel.writeAndFlush(resp);
                }
            });
        }
    }

    /**
     *
     * @param channel
     * @param data 是Packet封装好的字节数组
     */
    public static void write(Channel channel, byte[] data) {
        if(channel != null && channel.isWritable()) {
            ByteBuf buf = channel.alloc().directBuffer();
            BinaryWebSocketFrame resp = new BinaryWebSocketFrame(buf.writeBytes(data));
            channel.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    channel.writeAndFlush(resp);
                }
            });
        }
    }
}
