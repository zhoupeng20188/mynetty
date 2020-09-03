package com.zp.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author zp
 * @create 2020/9/1 18:12
 */
public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组，管理所有channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mmm:ss");

    /**
     * 当连接建立时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush(sdf.format(new Date()) + " [客户端]"+ctx.channel().remoteAddress()+" 加入聊天");
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(sdf.format(new Date()) + " [客户端]"+ctx.channel().remoteAddress()+" 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(sdf.format(new Date()) + " [客户端]"+ctx.channel().remoteAddress()+" 下线了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush(sdf.format(new Date()) + " [客户端]"+ctx.channel().remoteAddress()+" 离线了");
        System.out.println("channelGroup size=" + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(cg ->{
            // 不是自己时，转发消息
            if(cg  != channel){
                cg.writeAndFlush(sdf.format(new Date()) + " [客户端]"+channel.remoteAddress()+"说："+s);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
