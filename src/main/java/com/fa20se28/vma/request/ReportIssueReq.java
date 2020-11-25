package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestType;

public class ReportIssueReq {
    private RequestType requestType;
    private String description;
    private String vehicleId;

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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
