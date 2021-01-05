package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.VehicleStatus;

public class Vehicle {
    private String vehicleId;
    private String model;
    private VehicleType vehicleType;
    private Seat seatsModel;
    private VehicleStatus vehicleStatus;
    private float distanceDriven;

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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Seat getSeatsModel() {
        return seatsModel;
    }

    public void setSeatsModel(Seat seatsModel) {
        this.seatsModel = seatsModel;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public float getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(float distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleStatus=" + vehicleStatus +
                '}';
    }
}
