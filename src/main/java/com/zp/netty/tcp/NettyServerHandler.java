package com.zp.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author zp
 * @create 2020/9/1 18:12
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private  int cnt;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);

        String s = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("从客户端收到消息："+s);
        System.out.println("接收次数："+ (++cnt));

        // 给客户端回送一个随机数
        ByteBuf byteBuf1 = Unpooled.copiedBuffer(UUID.randomUUID().toString()+"\n", CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(byteBuf1);
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
