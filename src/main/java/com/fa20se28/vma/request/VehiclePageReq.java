package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.VehicleStatus;

public class VehiclePageReq {
    private String vehicleId;
    private String model;
    private int vehicleTypeId;
    private int seatsMin;
    private int seatsMax;
    private VehicleStatus vehicleStatus;
    private float vehicleMinDis;
    private float vehicleMaxDis;

    public VehiclePageReq(String vehicleId, String model, int vehicleTypeId, int seatsMin, int seatsMax, VehicleStatus vehicleStatus, float vehicleMinDis, float vehicleMaxDis) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleTypeId = vehicleTypeId;
        this.seatsMin = seatsMin;
        this.seatsMax = seatsMax;
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

    public int getSeatsMin() {
        return seatsMin;
    }

    public void setSeatsMin(int seatsMin) {
        this.seatsMin = seatsMin;
    }

    public int getSeatsMax() {
        return seatsMax;
    }

    public void setSeatsMax(int seatsMax) {
        this.seatsMax = seatsMax;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
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
}
