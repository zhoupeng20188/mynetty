package com.zp.netty.rpc.consumer;

import com.zp.netty.rpc.api.HelloService;

/**
 * @Author zp
 * @create 2020/9/7 16:47
 */
public class MyRpcConsumer {
    public static void main(String[] args) throws InterruptedException {
        HelloService proxy = (HelloService) new NettyClient().getProxy(HelloService.class, "zpmsg://helloService/hello/");
        System.out.println(proxy.hello("jms"));
    }
}
