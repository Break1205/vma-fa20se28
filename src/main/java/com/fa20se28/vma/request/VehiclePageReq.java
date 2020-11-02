package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.VehicleStatus;

public class VehiclePageReq {
    private String vehicleId;
    private String model;
    private int vehicleTypeId;
    private VehicleStatus vehicleStatus;
    private float vehicleMinDis;
    private float vehicleMaxDis;

    public VehiclePageReq(String vehicleId, String model, int vehicleTypeId, VehicleStatus vehicleStatus, float vehicleMinDis, float vehicleMaxDis) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleTypeId = vehicleTypeId;
        this.vehicleStatus = vehicleStatus;
        this.vehicleMinDis = vehicleMinDis;
        this.vehicleMaxDis = vehicleMaxDis;
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

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
