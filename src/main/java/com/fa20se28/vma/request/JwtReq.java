package com.fa20se28.vma.request;

import java.io.Serializable;

public class JwtReq implements Serializable {
    private String userId;
    private String password;

    public JwtReq() {
    }

    public JwtReq(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}