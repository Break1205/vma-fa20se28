package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestType;

public class VehicleChangeRequestReq {
    private RequestType requestType;
    private String description;

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
}
