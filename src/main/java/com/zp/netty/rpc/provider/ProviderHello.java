package com.zp.netty.rpc.provider;

import com.zp.netty.rpc.api.HelloService;

/**
 * @Author zp
 * @create 2020/9/7 16:48
 */
public class ProviderHello implements HelloService {
    @Override
    public String hello(String msg) {
        return "hello," + msg;
    }
}
