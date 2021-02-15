package pre.cg.ws.websocket.handler;

import com.google.common.base.Utf8;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
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
        //字符串解码和编码
//        pipeline.addLast("decoder", new StringDecoder());
//        pipeline.addLast("encoder", new StringEncoder());

        // 链接idle状态检测，服务端10s内没有收到客户端数据判断为非活跃链接并主动断开链接
        pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));

        //解析Http协议
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));

        pipeline.addLast(new HttpServerHandler());
    }
}
