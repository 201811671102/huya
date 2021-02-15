package com.cs.heart_release_sock.Netty;

import com.cs.heart_release_sock.base.config.WSConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description websocket服务器
 *
 */
@Slf4j
@Component
public class WebsocketServer {

    public  void startup() {
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        Class<? extends ServerSocketChannel> serverSocketChannelClass;
        if (Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup();
            serverSocketChannelClass = EpollServerSocketChannel.class;
        } else {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            serverSocketChannelClass = NioServerSocketChannel.class;
        }

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .channel(serverSocketChannelClass)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_RCVBUF, 1024 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    .childHandler(new SocketChannelInitializer());
            if (Epoll.isAvailable()) {
                bootstrap.option(EpollChannelOption.SO_REUSEPORT, true);
            }
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            ChannelFuture future = bootstrap.bind(WSConfig.port);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("websocket server startup at port:" + WSConfig.port);
                    } else {
                        log.error("websocket server startup failed!!! port:" + WSConfig.port);
                        System.exit(-1);
                    }
                }
            });
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("websocket server start error.", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
