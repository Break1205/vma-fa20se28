package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestType;

public class VehicleRequestReq {
    private RequestType requestType;
    private String description;
    private String vehicleDocumentId;

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

    public String getVehicleDocumentId() {
        return vehicleDocumentId;
    }

    public void setVehicleDocumentId(String vehicleDocumentId) {
        this.vehicleDocumentId = vehicleDocumentId;
    }
}
