package com.fa20se28.vma.response;

import com.fa20se28.vma.model.TripHistory;
import com.fa20se28.vma.model.AssignedVehicle;

import java.util.List;

public class VehicleCurrentRes {
    private AssignedVehicle vehicleCurrent;
    private List<TripHistory> tripHistory;

    public VehicleCurrentRes(AssignedVehicle vehicleCurrent, List<TripHistory> tripHistory) {
        this.vehicleCurrent = vehicleCurrent;
        this.tripHistory = tripHistory;
    }

    public AssignedVehicle getVehicleCurrent() {
        return vehicleCurrent;
    }

    public void setVehicleCurrent(AssignedVehicle vehicleCurrent) {
        this.vehicleCurrent = vehicleCurrent;
    }

    public List<TripHistory> getTripHistory() {
        return tripHistory;
    }

    public void setTripHistory(List<TripHistory> tripHistory) {
        this.tripHistory = tripHistory;
    }
}
