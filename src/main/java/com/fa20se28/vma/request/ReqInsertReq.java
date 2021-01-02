package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;

import java.time.LocalDateTime;

public class ReqInsertReq {
    private int requestId;
    private String userId;
    private String userDocumentId;
    private String vehicleId;
    private int vehicleDocId;
    private RequestStatus requestStatus;
    private RequestType requestType;
    private String desc;
    private LocalDateTime createDate;

    public ReqInsertReq(String userId, String userDocumentId, String vehicleId, int vehicleDocId, RequestStatus requestStatus, RequestType requestType, String desc, LocalDateTime createDate) {
        this.userId = userId;
        this.userDocumentId = userDocumentId;
        this.vehicleId = vehicleId;
        this.vehicleDocId = vehicleDocId;
        this.requestStatus = requestStatus;
        this.requestType = requestType;
        this.desc = desc;
        this.createDate = createDate;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getVehicleDocId() {
        return vehicleDocId;
    }

    public void setVehicleDocId(int vehicleDocId) {
        this.vehicleDocId = vehicleDocId;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
