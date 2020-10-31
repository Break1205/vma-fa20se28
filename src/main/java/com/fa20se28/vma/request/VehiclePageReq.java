package com.fa20se28.vma.request;

public class VehiclePageReq {
    private String vehicleId;
    private String model;
    private int vehicleTypeId;
    private String vehicleStatus;
    private float vehicleMinDis;
    private float vehicleMaxDis;

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

    public float getVehicleMinDis() {
        return vehicleMinDis;
    }

    public void setVehicleMinDis(float vehicleMinDis) {
        this.vehicleMinDis = vehicleMinDis;
    }

    public float getVehicleMaxDis() {
        return vehicleMaxDis;
    }

    public void setVehicleMaxDis(float vehicleMaxDis) {
        this.vehicleMaxDis = vehicleMaxDis;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
