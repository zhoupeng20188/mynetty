package com.zp.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO的channel将文件1读出并拷贝到文件2
 * 使用一个buffer
 *
 * @Author zp
 * @create 2020/8/31 14:19
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {


        FileInputStream fileInputStream = new FileInputStream("1.txt");

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

        // 从输入流拿到channel
        FileChannel channel = fileInputStream.getChannel();

        FileChannel channel02 = fileOutputStream.getChannel();

        // 创建文件大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {

            byteBuffer.clear();
            // 将byteBuffer中的数据写入channel
            int read = channel.read(byteBuffer);
            if (read == -1) {
                break;
            } else {
                byteBuffer.flip();
                channel02.write(byteBuffer);
            }
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
