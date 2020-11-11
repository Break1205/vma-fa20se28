package com.fa20se28.vma.response;

import com.fa20se28.vma.model.AssignedVehicle;

public class VehicleCurrentRes {
    private AssignedVehicle vehicleCurrent;

    public VehicleCurrentRes(AssignedVehicle vehicleCurrent) {
        this.vehicleCurrent = vehicleCurrent;
    }

    public AssignedVehicle getVehicleCurrent() {
        return vehicleCurrent;
    }

    public void setVehicleCurrent(AssignedVehicle vehicleCurrent) {
        this.vehicleCurrent = vehicleCurrent;
    }
}
