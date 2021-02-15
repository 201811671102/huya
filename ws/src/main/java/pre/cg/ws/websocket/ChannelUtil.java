package pre.cg.ws.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import pre.cg.ws.base.Packet;

import java.io.IOException;

/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description 服务端回包工具类，封装回包逻辑
 */
@Slf4j
public class ChannelUtil {

    public static void write(ChannelHandlerContext ctx, String packet) throws IOException {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(packet);
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(textWebSocketFrame);
            }
        });
    }

    public static void write(Channel channel, String packet) throws IOException {
        if(channel != null && channel.isWritable()) {
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(packet);
            channel.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    channel.writeAndFlush(textWebSocketFrame);
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
