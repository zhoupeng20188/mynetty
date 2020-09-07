package com.zp.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author zp
 * @create 2020/9/1 18:02
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 默认个数为cpu核心数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 默认个数为cpu核心数*2
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到的连接数
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 给pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 添加自定义解码器
                        socketChannel.pipeline().addLast(new MyProtocolDecodeHandler());
                        // 添加自定义编码器
                        socketChannel.pipeline().addLast(new MyProtocolEncodeHandler());
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });

        System.out.println("server is ready...");
        // 启动服务器
        ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
        // 对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();;
    }
}
