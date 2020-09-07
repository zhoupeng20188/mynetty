package com.zp.netty.rpc.provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author zp
 * @create 2020/9/1 18:12
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str  = msg.toString();
        System.out.println(str);
        if(str.startsWith("zpmsg://helloService/hello/")){
            String s = str.substring(str.lastIndexOf('/')  + 1);
            System.out.println("从客户端收到消息："+s);
            // 给客户端返回结果
            String hello = new ProviderHello().hello(s);
            ctx.writeAndFlush(hello);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
