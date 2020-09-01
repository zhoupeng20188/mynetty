package com.zp.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author zp
 * @create 2020/9/1 10:04
 */
public class ChatClient {
    private Selector selector;

    private SocketChannel socketChannel;

    private static String HOST = "127.0.0.1";

    private static int PORT = 6667;

    private String name;

    // 初始化
    public ChatClient() throws IOException {
        selector = Selector.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        socketChannel = SocketChannel.open(inetSocketAddress);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        name = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(name + " is ok..");
    }

    public void sendMsg(String msg) {
        String str = name + "说：" + msg;
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        try {
            socketChannel.write(byteBuffer);
            System.out.println("我说："+ msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            int select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        channel.read(byteBuffer);
                        System.out.println(new String(byteBuffer.array()));
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();
        //启动一个线程
        new Thread(){
           @Override
           public void run(){
               while (true){
                   // 每隔2秒读取消息
                   chatClient.readMsg();

                   try {
                       Thread.sleep(2000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
        }.start();

        // 从控制台接收并发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendMsg(s);
        }
    }
}
