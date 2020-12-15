package com.zp.netty.protoBuf;

import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

/**
 * @Author zp
 * @create 2020/12/9 16:45
 */
public class FileUtil {
    public static void write(File file, String s) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(s.getBytes(Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(File file, byte[] bytes) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeOverride(File file, ByteString byteString) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteString.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(File file, ByteBuf byteBuf) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            byte[] req = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(req);
            fileOutputStream.write(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(File file, int start, int bytes) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[bytes];
            fileInputStream.read(buffer, start, bytes);
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] convertFileToByteArray(File file) {
        byte[] data = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fileInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray();

            fileInputStream.close();
            baos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ByteString convertFileToByteString(File file) {
        return ByteString.copyFrom(convertFileToByteArray(file));

    }
}
