package com.zp.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO的channel将文件内容读出
 * @Author zp
 * @create 2020/8/31 14:19
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {


        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 从输入流拿到channel
        FileChannel channel = fileInputStream.getChannel();

        // 创建文件大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将byteBuffer中的数据写入channel
        channel.read(byteBuffer);
        // 将byteBuffer转成String输出
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
