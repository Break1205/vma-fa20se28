package com.fa20se28.vma.request;

public class VehicleDropDownReq {
    private String vehicleId;
    private String model;
    private String vehicleTypeName;

    public VehicleDropDownReq(String vehicleId, String model, String vehicleTypeName) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }
}
