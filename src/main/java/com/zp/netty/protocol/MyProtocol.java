package com.zp.netty.protocol;

/**
 * @Author zp
 * @create 2020/9/7 12:50
 */
public class MyProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
