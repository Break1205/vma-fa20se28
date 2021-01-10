package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleContract;

import java.util.List;

public class VehicleContractRes {
    private List<List<Integer>> combinations;
    private List<VehicleContract> vehicleList;

    public VehicleContractRes(List<List<Integer>> combinations, List<VehicleContract> vehicleList) {
        this.combinations = combinations;
        this.vehicleList = vehicleList;
    }

    public List<List<Integer>> getCombinations() {
        return combinations;
    }

    public void setCombinations(List<List<Integer>> combinations) {
        this.combinations = combinations;
    }

    public List<VehicleContract> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleContract> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
