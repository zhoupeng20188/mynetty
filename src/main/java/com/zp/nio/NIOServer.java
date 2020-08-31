package com.zp.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author zp
 * @create 2020/8/31 17:05
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        // 得到ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到Selector
        Selector selector = Selector.open();
        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 将serverSocketChannel注册到selector，事件为连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if(selector.select(1000) == 0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            // 如果返回>0，得到关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys的size=" + selectionKeys.size());

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                if(next.isAcceptable()){
                    // 如果为连接事件，则为当前客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功！生成一个socketChannel"+ socketChannel.hashCode());
                    // 将此通道注册到selector，事件为读，同时给此channel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(next.isReadable()){
                    // 如果读事件
                    // 通过selectionKey得到channel
                    SocketChannel channel = (SocketChannel) next.channel();
                    // 拿到此channel对应的buffer
                    ByteBuffer buffer = (ByteBuffer) next.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));
                }

                // 手动从集合中删除当前元素，防止重复操作
                iterator.remove();
            }
        }

    }
}
