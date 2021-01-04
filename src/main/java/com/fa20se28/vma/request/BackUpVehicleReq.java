package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestStatus;

import java.util.List;

public class BackUpVehicleReq {
    private RequestStatus requestStatus;
    private int requestId;
    private List<String> vehiclesId;
    private String brokenVehicleLocation;
    private boolean isFar;

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public List<String> getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(List<String> vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    public String getBrokenVehicleLocation() {
        return brokenVehicleLocation;
    }

    public void setBrokenVehicleLocation(String brokenVehicleLocation) {
        this.brokenVehicleLocation = brokenVehicleLocation;
    }

    public boolean isFar() {
        return isFar;
    }

    public void setFar(boolean far) {
        isFar = far;
    }
}
