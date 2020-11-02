package com.fa20se28.vma.request;

public class VehicleDropDownReq {
    private String vehicleId;
    private String model;
    private int vehicleTypeId;

    public VehicleDropDownReq(String vehicleId, String model, int vehicleTypeId) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleTypeId = vehicleTypeId;
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

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
