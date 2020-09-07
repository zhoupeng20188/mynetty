package com.zp.netty.rpc.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * @Author zp
 * @create 2020/9/1 18:24
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    /**
     * channelHandler上下文
     */
    private ChannelHandlerContext chc;

    /**
     * 返回结果
     */
    private String  result;

    /**
     * 方法参数
     */
    private String args;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive is called");
        this.chc = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead is called");
        this.result = (String) msg;
        notify();
    }

    /**
     * 被代理对象调用，发送数据给服务器
     * ->wait, ->被channelRead0唤醒，->返回结果
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call1 is called");
        this.chc.writeAndFlush(args);
        wait();
        System.out.println("call2 is called");
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public ChannelHandlerContext getChc() {
        return chc;
    }

    public void setChc(ChannelHandlerContext chc) {
        this.chc = chc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
