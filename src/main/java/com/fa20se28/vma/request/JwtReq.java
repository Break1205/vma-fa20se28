package com.fa20se28.vma.request;

import java.io.Serializable;

public class JwtReq implements Serializable {
    private String phoneNumber;
    private String password;

    public JwtReq() {
    }

    public JwtReq(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}