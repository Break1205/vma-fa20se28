package com.fa20se28.vma.request;

public class VehicleDropDownReq {
    private String vehicleId;
    private String model;
    private int vehicleTypeId;
    private int seatsMin;
    private int seatsMax;

    public VehicleDropDownReq(String vehicleId, String model, int vehicleTypeId, int seatsMin, int seatsMax) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.vehicleTypeId = vehicleTypeId;
        this.seatsMin = seatsMin;
        this.seatsMax = seatsMax;
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
}
