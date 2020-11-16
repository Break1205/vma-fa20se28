package com.fa20se28.vma.model;

import java.io.ByteArrayInputStream;

public class ByteArrayInputStreamWrapper {
    private ByteArrayInputStream byteArrayInputStream;
    private int byteCount;


    public ByteArrayInputStream getByteArrayInputStream() {
        return byteArrayInputStream;
    }


    public void setByteArrayInputStream(ByteArrayInputStream byteArrayInputStream) {
        this.byteArrayInputStream = byteArrayInputStream;
    }


    public int getByteCount() {
        return byteCount;
    }


    public void setByteCount(int byteCount) {
        this.byteCount = byteCount;
    }
}
