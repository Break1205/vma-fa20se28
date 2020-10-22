package com.fa20se28.vma.request;

public class VehiclePageReq {
    private String vehicleId;
    private String model;
    private int vehicleTypeId;
    private float vehicleMinDis;
    private float vehicleMaxDis;
    private int vehicleStatusId;

    public VehiclePageReq(String vehicleId, String model, int vehicleTypeId, float vehicleMinDis, float vehicleMaxDis, int vehicleStatusId) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleTypeId = vehicleTypeId;
        this.vehicleMinDis = vehicleMinDis;
        this.vehicleMaxDis = vehicleMaxDis;
        this.vehicleStatusId = vehicleStatusId;
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

    public int getVehicleStatusId() {
        return vehicleStatusId;
    }

    public void setVehicleStatusId(int vehicleStatusId) {
        this.vehicleStatusId = vehicleStatusId;
    }
}
