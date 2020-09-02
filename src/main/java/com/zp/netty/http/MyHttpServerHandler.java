package com.zp.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author zp
 * @create 2020/9/2 10:52
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if(httpObject instanceof HttpRequest){
            System.out.println("客户端地址："+channelHandlerContext.channel().remoteAddress());

            // 回复信息给浏览器，http协议
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是server", CharsetUtil.UTF_8);

            // 构建一 个httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            // 将response返回
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
