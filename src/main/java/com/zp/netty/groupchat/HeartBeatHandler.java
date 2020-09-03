package com.zp.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author zp
 * @create 2020/9/3 16:40
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent  ise  = (IdleStateEvent) evt;
            IdleState state = ise.state();
            switch (state){
                case READER_IDLE:
                    System.out.println("读空闲");
                    break;
                case WRITER_IDLE:
                    System.out.println("写空闲");
                    break;
                case ALL_IDLE:
                    System.out.println("读写空闲");
                    break;
            }
        }
    }
}
