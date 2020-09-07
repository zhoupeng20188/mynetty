package com.zp.netty.rpc.consumer;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @Author zp
 * @create 2020/9/1 18:18
 */
public class NettyClient {
    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private NettyClientHandler nettyClientHandler;

    public Object getProxy(Class inter, String providerName) throws InterruptedException {

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{inter}, (proxy, method, args) -> {
                    // 调用接口方法时会执行这段代码
                    System.out.println("invocationHandler is called");
                    System.out.println(nettyClientHandler);
                    if (nettyClientHandler == null) {
                        this.start();
                    }
                    nettyClientHandler.setArgs(providerName + args[0]);
                    System.out.println(nettyClientHandler);
                    // 将nettyClientHandler提交到线程池中执行
                    // 会执行其call()方法
                    return executor.submit(nettyClientHandler).get();
                });
    }

    public void start() throws InterruptedException {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        nettyClientHandler = new NettyClientHandler();
//        try {
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringEncoder());
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(nettyClientHandler);
                        }
                    });
            // 客户端连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            System.out.println("client is ready...");

//            channelFuture.channel().closeFuture().sync();
//        } finally {
//            nioEventLoopGroup.shutdownGracefully();
//        }
    }
}
