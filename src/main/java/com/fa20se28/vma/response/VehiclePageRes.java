package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Vehicle;

import java.util.List;

public class VehiclePageRes {
    private int totalVehicles;
    private List<Vehicle> vehicleList;

    public VehiclePageRes(int totalVehicles, List<Vehicle> vehicleList) {
        this.totalVehicles = totalVehicles;
        this.vehicleList = vehicleList;
    }

    public int getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(int totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
