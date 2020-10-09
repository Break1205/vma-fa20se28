package com.fa20se28.vma.request;

public class VehiclePageReq {
    private String vehicleId;
    private String model;
    private String vehicleType;
    private Float vehicleMinDis;
    private Float vehicleMaxDis;
    private String vehicleStatus;

    public VehiclePageReq(String vehicleId, String model, String vehicleType, Float vehicleMinDis, Float vehicleMaxDis, String vehicleStatus) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleType = vehicleType;
        this.vehicleMinDis = vehicleMinDis;
        this.vehicleMaxDis = vehicleMaxDis;
        this.vehicleStatus = vehicleStatus;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Float getVehicleMinDis() {
        return vehicleMinDis;
    }

    public void setVehicleMinDis(Float vehicleMinDis) {
        this.vehicleMinDis = vehicleMinDis;
    }

    public Float getVehicleMaxDis() {
        return vehicleMaxDis;
    }

    public void setVehicleMaxDis(Float vehicleMaxDis) {
        this.vehicleMaxDis = vehicleMaxDis;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
