package com.zp.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Author zp
 * @create 2020/9/3 17:35
 */
public class WebSocketHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息：" + textWebSocketFrame.text());
        // 回复消息
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 "+ LocalDateTime.now()
        + ":" + textWebSocketFrame.text()));
        System.out.println("服务器回发消息成功");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // longText是唯一的id
        System.out.println("handlerAdded" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
