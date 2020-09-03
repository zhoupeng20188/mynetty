package com.zp.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author zp
 * @create 2020/9/1 18:02
 */
public class NettyChatServer {
    public static void main(String[] args) throws InterruptedException {
        // 默认个数为cpu核心数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 默认个数为cpu核心数*2
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到的连接数
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 给pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("decoder", new StringDecoder());
                        socketChannel.pipeline().addLast("encoder", new StringEncoder());
                        socketChannel.pipeline().addLast(new NettyChatServerHandler());
                        // 加入心跳检测,会在下一个handler中处理
                        socketChannel.pipeline().addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                        socketChannel.pipeline().addLast(new HeartBeatHandler());
                    }
                });

        System.out.println("server is ready...");
        // 启动服务器
        ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
        // 对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();
    }
}
