package com.zp.netty.rpc.provider;

/**
 * @Author zp
 * @create 2020/9/7 16:46
 */
public class MyRpcProvider {
    public static void main(String[] args) throws InterruptedException {
        new NettyServer().start();
    }
}
