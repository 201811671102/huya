package com.cs.heart_release_sock.Netty;

import com.cs.heart_release_sock.handler.HttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 *
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 链接idle状态检测，服务端30s内没有收到客户端数据判断为非活跃链接并主动断开链接
        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
        //编码解码器 HttpRequestDecoder和HttpResponseEncoder的一个组合，针对http协议进行编解码
        pipeline.addLast(new HttpServerCodec());
        //将多个消息转换成单一的消息对象
        pipeline.addLast(new HttpObjectAggregator(65536));
        // 分块向客户端写数据，防止发送大文件时导致内存溢出， channel.write(new ChunkedFile(new File("bigFile.mkv")))
       // pipeline.addLast(new ChunkedWriteHandler());
        // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
       // pipeline.addLast(new WebSocketServerCompressionHandler());
        // 服务器端向外暴露的 web socket 端点，当客户端传递比较大的对象时，maxFrameSize参数的值需要调大
      //  pipeline.addLast(new WebSocketServerProtocolHandler("/parking", null, true, 10485760))
        pipeline.addLast(new HttpServerHandler());

    }
}
