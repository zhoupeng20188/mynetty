package com.zp.netty.groupchat;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author zp
 * @create 2020/9/1 18:18
 */
public class NettyChatClient {
    public static void main(String[] args) throws InterruptedException {

            NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decoder", new StringDecoder());
                            socketChannel.pipeline().addLast("encoder", new StringEncoder());
                            socketChannel.pipeline().addLast(new NettyChatClientHandler());
                        }
                    });
            System.out.println("client is ready...");
            // 客户端连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668)
                    .sync();
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String s = scanner.nextLine();
                channelFuture.channel().writeAndFlush(s);
            }
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
