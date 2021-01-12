package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleContract;

import java.util.List;
import java.util.Map;

public class VehicleContractRes {
    private Map<Integer, List<Integer>> combinations;
    private List<VehicleContract> vehicleList;

    public VehicleContractRes(Map<Integer, List<Integer>> combinations, List<VehicleContract> vehicleList) {
        this.combinations = combinations;
        this.vehicleList = vehicleList;
    }

    public Map<Integer, List<Integer>> getCombinations() {
        return combinations;
    }

    public void setCombinations(Map<Integer, List<Integer>> combinations) {
        this.combinations = combinations;
    }

    public List<VehicleContract> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleContract> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
