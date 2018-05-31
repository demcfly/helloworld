package io.zxt.helloworld.nettyDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.zxt.helloworld.nettyDemo.http.handler.SimpleHelloWorldHandler;

public class NettyServer implements Server{

    private String host;
    private int port;

    NioEventLoopGroup eventLoopGroup;
    ServerBootstrap serverBootstrap;

    public void startService(EndpointConfig endpointConfig) throws Exception {
        host = endpointConfig.getHost();
        port = endpointConfig.getPort();

        serverBootstrap = new ServerBootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        serverBootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpServerCodec());
                        ch.pipeline().addLast(new HttpObjectAggregator(512*1024));
                        ch.pipeline().addLast(new HttpServerExpectContinueHandler());
                        ch.pipeline().addLast(new SimpleHelloWorldHandler());
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = serverBootstrap.bind(port).sync();
        future.channel().closeFuture().addListener((cf) -> {
            System.out.println("Shutting down NettyServer.");
        });
    }

    public void stopService() {

    }

    public String inspectStatistics() throws Exception {
        return "OK\n";
    }
}
