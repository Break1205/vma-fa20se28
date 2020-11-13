package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.VehicleStatus;

public class Vehicle {
    private String vehicleId;
    private String model;
    private String vehicleTypeName;
    private int seats;
    private VehicleStatus vehicleStatus;
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public float getVehicleDistance() {
        return vehicleDistance;
    }

    public void setVehicleDistance(float vehicleDistance) {
        this.vehicleDistance = vehicleDistance;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleStatus=" + vehicleStatus +
                '}';
    }
}
