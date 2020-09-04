package com.zp.netty.handler.chain;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author zp
 * @create 2020/9/1 18:24
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Long> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("从服务端接收数据："+aLong);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(123456L);
        /**
         * 如果发送字符串"abcdabcdefghefgh"
         * 则占16字节，而客户端是每次读Long，即8字节，所以会读2次
         */
//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdefghefgh",CharsetUtil.UTF_8));
    }
}
