package com.zp.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zp
 * @create 2020/8/31 11:39
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Bio Server启动成功");
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        while (true){
            System.out.println("accept...阻塞");
            Socket socket = serverSocket.accept();
            newCachedThreadPool.execute(()->{
                handle(socket);
            });
        }
    }

    public static void handle(Socket socket){
        System.out.println("客户端连接成功 " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
        byte[] buffer =new byte[1024];
        try {
            while(true){
                InputStream inputStream = socket.getInputStream();
                System.out.println("read...阻塞");
                int read = inputStream.read(buffer);
                if(read != -1){
                    System.out.println(new String(buffer, 0, read));
                } else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
