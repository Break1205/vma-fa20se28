package com.fa20se28.vma.model;

public class Vehicle {
    private String vehicleId;
    private String model;
    private String vehicleTypeName;
    private String vehicleStatusName;
    private float vehicleDistance;

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

    public String getVehicleStatusName() {
        return vehicleStatusName;
    }

    public void setVehicleStatusName(String vehicleStatusName) {
        this.vehicleStatusName = vehicleStatusName;
    }

    public float getVehicleDistance() {
        return vehicleDistance;
    }

    public void setVehicleDistance(float vehicleDistance) {
        this.vehicleDistance = vehicleDistance;
    }
}
