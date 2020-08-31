package com.zp.nio;

import javax.swing.plaf.FileChooserUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer可以使用堆外内存，直接对文件修改，不用拷贝文件
 * @Author zp
 * @create 2020/8/31 16:20
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /***
         * 第二个参数为直接修改的开始位置
         * 第三个参数为映射到内存的大小，即将1.txt的多少个字节映射到内存
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'H');
        map.put(3, (byte) '9');
        randomAccessFile.close();
        System.out.println("修改成功");

    }
}
