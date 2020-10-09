package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Vehicle;

import java.util.List;

public class VehiclePageRes {
    private String atPage;
    private List<Vehicle> vehicleList;

    public VehiclePageRes(String atPage, List<Vehicle> vehicleList) {
        this.atPage = atPage;
        this.vehicleList = vehicleList;
    }

    public String getAtPage() {
        return atPage;
    }

    public void setAtPage(String atPage) {
        this.atPage = atPage;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
