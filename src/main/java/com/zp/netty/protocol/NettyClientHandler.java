package com.zp.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author zp
 * @create 2020/9/1 18:24
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyProtocol myProtocol = new MyProtocol();
        for (int i = 0; i < 10; i++) {
            String str = "hello server" + i;
            byte[] bytes = str.getBytes(CharsetUtil.UTF_8);
            myProtocol.setLen(bytes.length);
            myProtocol.setContent(bytes);
            ctx.writeAndFlush(myProtocol);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol) throws Exception {

    }
}
