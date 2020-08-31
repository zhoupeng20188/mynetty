package com.zp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author zp
 * @create 2020/8/31 17:21
 */
public class NIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        // 设置为非阻塞
        socketChannel.configureBlocking(false);
        // 指定服务器的地址和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不支阻塞，可以做其它工作");
            }
        }

        // 连接成功
        String str = "hello,nio";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
