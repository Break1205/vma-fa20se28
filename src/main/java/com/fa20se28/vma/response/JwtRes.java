package com.fa20se28.vma.response;

import java.io.Serializable;

public class JwtRes implements Serializable {
    private final String jwt;

    public JwtRes(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
