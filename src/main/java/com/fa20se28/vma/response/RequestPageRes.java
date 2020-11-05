package com.fa20se28.vma.response;

import com.fa20se28.vma.response.RequestRes;

import java.util.List;

public class RequestPageRes {
    private List<RequestRes> requestRes;

    public List<RequestRes> getRequestRes() {
        return requestRes;
    }

    public void setRequestRes(List<RequestRes> requestRes) {
        this.requestRes = requestRes;
    }
}
