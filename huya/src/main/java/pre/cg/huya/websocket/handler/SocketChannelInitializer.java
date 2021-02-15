package pre.cg.huya.websocket.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
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
        // 链接idle状态检测，服务端10s内没有收到客户端数据判断为非活跃链接并主动断开链接
        pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.MINUTES));
        //编码解码器
        pipeline.addLast(new HttpServerCodec());
        //将多个消息转换成单一的消息对象
        pipeline.addLast(new HttpObjectAggregator(65536));

        pipeline.addLast(new HttpServerHandler());

    }
}
