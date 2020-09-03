package com.zp.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author zp
 * @create 2020/9/1 18:02
 */
public class NettyWebSocketServer {
    public static void main(String[] args) throws InterruptedException {
        // 默认个数为cpu核心数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 默认个数为cpu核心数*2
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 因为基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 是以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // http数据在传输中是分段，HttpObjectAggregator可以将多段合起来
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            // 对应websocket请求，ws://localhost:6668/hello
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 自定义handler
                            pipeline.addLast(new WebSocketHandler());
                        }
                    });

            System.out.println("server is ready...");
            // 启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(9008).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
