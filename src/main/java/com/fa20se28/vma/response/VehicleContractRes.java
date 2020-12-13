package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleContract;

import java.util.List;

public class VehicleContractRes {
    private List<VehicleContract> vehicleList;

    public VehicleContractRes(List<VehicleContract> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<VehicleContract> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleContract> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
