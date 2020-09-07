package com.zp.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author zp
 * @create 2020/9/4 17:34
 */
public class MyProtocolDecodeHandler extends ByteToMessageDecoder {
    /**
     * 会根据接收到的数据调用多次
     * 直到没有新的元素添加到list，或者byteBuf没有可读的数据
     * 如果list内容不为空，则会把数据传给业务handler中，即NettyServerHandler
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyProtocolDecodeHandler decode is called");
        int i = byteBuf.readInt();
        byte[] buffer = new byte[i];
        byteBuf.readBytes(buffer);
        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setLen(i);
        myProtocol.setContent(buffer);
        list.add(myProtocol);
    }
}
