package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestType;

public class RequestReq {
    private RequestType requestType;
    private String description;
    private UserDocumentReq userDocumentReq;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDocumentReq getUserDocumentReq() {
        return userDocumentReq;
    }

    public void setUserDocumentReq(UserDocumentReq userDocumentReq) {
        this.userDocumentReq = userDocumentReq;
    }
}
