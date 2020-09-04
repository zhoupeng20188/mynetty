package com.zp.netty.protoBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author zp
 * @create 2020/9/1 18:12
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 从客户端读取
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("收到客户端数据："+student.getId() +","+student.getName());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 写数据并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
