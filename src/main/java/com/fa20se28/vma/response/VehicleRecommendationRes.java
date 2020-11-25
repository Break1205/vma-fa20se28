package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleRecommendation;

import java.util.List;

public class VehicleRecommendationRes {
    private List<VehicleRecommendation> vehicleList;

    public VehicleRecommendationRes(List<VehicleRecommendation> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<VehicleRecommendation> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleRecommendation> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
