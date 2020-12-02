package com.fa20se28.vma.response;

import com.fa20se28.vma.model.AssignedVehicle;

import java.util.List;

public class VehicleHistoryRes {
    private List<AssignedVehicle> vehicleHistory;

    public VehicleHistoryRes(List<AssignedVehicle> vehicleHistory) {
        this.vehicleHistory = vehicleHistory;
    }

    public List<AssignedVehicle> getVehicleHistory() {
        return vehicleHistory;
    }

    public void setVehicleHistory(List<AssignedVehicle> vehicleHistory) {
        this.vehicleHistory = vehicleHistory;
    }
}
