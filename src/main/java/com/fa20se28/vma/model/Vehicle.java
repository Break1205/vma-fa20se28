package com.fa20se28.vma.model;

public class Vehicle {
    private String vehicleId;
    private String model;
    private String vehicleTypeId;
    private String vehicleStatusId;
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

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getVehicleStatusId() {
        return vehicleStatusId;
    }

    public void setVehicleStatusId(String vehicleStatusId) {
        this.vehicleStatusId = vehicleStatusId;
    }

    public float getVehicleDistance() {
        return vehicleDistance;
    }

    public void setVehicleDistance(float vehicleDistance) {
        this.vehicleDistance = vehicleDistance;
    }
}
