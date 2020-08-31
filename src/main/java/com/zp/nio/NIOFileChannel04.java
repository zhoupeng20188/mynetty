package com.zp.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO的channel的transferFrom将文件拷贝
 *
 * @Author zp
 * @create 2020/8/31 14:19
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {
        
        FileInputStream fileInputStream = new FileInputStream("a.jpg");

        FileOutputStream fileOutputStream = new FileOutputStream("b.jpg");

        // 从输入流拿到channel
        FileChannel sourceChannel = fileInputStream.getChannel();

        FileChannel destChannel = fileOutputStream.getChannel();

        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
