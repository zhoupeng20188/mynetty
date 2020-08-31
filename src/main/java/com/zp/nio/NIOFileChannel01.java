package com.zp.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO的channel将内容写入到文件
 * @Author zp
 * @create 2020/8/31 14:19
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String  str = "hello channel";

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");

        // 从输出流拿到channel
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());

        // 将byteBuffer反转
        byteBuffer.flip();

        // 将byteBuffer中的数据写入channel
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
