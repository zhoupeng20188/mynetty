package com.zp.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 聊天服务器
 * 可以感知用户上下线
 * 转发用户消息给其它用户
 * @Author zp
 * @create 2020/9/1 9:44
 */
public class ChartServer {
    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private static int PORT = 6667;

    /**
     * 初始化
     */
    public ChartServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public void listen() {
        while (true) {
            try {
                int cnt = selector.select(2000);
                if (cnt > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线");
                        }
                        if (key.isReadable()) {
                            System.out.println("接收消息....");
                            readMsg(key);
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readMsg(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = 0;
        try {
            read = channel.read(byteBuffer);

            String msg = new String(byteBuffer.array());
            if (read > 0) {
                System.out.println("from 【" + channel.getRemoteAddress() + "】:" + msg);
                transferMsg(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "下线");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给其它客户端
     *
     * @param msg
     * @param self
     */
    public void transferMsg(String msg, SocketChannel self) {
        Iterator<SelectionKey> iterator = selector.keys().iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    ((SocketChannel) channel).write(byteBuffer);
                    System.out.println("服务器转发消息给"+((SocketChannel) channel).getRemoteAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChartServer chartServer = new ChartServer();
        chartServer.listen();
    }
}
