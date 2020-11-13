package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Vehicle;

import java.util.List;

public class VehiclePageRes {
    private List<Vehicle> vehicleList;

    public VehiclePageRes(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
