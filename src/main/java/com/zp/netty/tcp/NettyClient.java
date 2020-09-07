package com.zp.netty.tcp;


import com.zp.netty.handler.chain.MyByteToLongHandler;
import com.zp.netty.handler.chain.MyLongToByteHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 多次运行客户端时结果不同，会出现粘包问题
 * @Author zp
 * @create 2020/9/1 18:18
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {

            NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("client is ready...");
            // 客户端连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668);

            channelFuture.channel().closeFuture().sync();
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
