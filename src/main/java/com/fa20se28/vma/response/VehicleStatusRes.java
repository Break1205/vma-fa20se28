package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleStatus;

import java.util.List;

public class VehicleStatusRes {
    private List<VehicleStatus> statusList;

    public VehicleStatusRes(List<VehicleStatus> statusList) {
        this.statusList = statusList;
    }

    public List<VehicleStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<VehicleStatus> statusList) {
        this.statusList = statusList;
    }
}
