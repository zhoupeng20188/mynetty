package com.zp.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author zp
 * @create 2020/9/4 17:20
 */
public class MyProtocolEncodeHandler extends MessageToByteEncoder<MyProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyProtocol myProtocol, ByteBuf byteBuf) throws Exception {
        System.out.println("MyProtocolEncodeHandler encode is called");
        int len = myProtocol.getLen();
        byte[] content = myProtocol.getContent();
        byteBuf.writeInt(len);
        byteBuf.writeBytes(content);
    }
}
