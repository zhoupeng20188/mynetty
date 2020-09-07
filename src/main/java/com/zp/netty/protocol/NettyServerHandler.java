package com.zp.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author zp
 * @create 2020/9/1 18:12
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {
        int len = myProtocol.getLen();
        byte[] content = myProtocol.getContent();
        System.out.println("从客户端读取到消息长度："+len);
        System.out.println("从客户端读取到消息："+ new String(content, CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.channel().writeAndFlush(123456L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
