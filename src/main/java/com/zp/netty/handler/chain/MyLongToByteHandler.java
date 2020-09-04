package com.zp.netty.handler.chain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author zp
 * @create 2020/9/4 17:20
 */
public class MyLongToByteHandler extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteHandler encode is called");
        System.out.println("encode :" + aLong);
        byteBuf.writeLong(aLong);
    }
}
