package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleType;

import java.util.List;

public class VehicleTypesRes {
    private List<VehicleType> vehicleTypes;

    public VehicleTypesRes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }
}
