package com.fa20se28.vma.model;

public class VehicleBasic {
    private int contractVehicleId;
    private String vehicleId;
    private VehicleType vehicleType;
    private int seats;

    public int getContractVehicleId() {
        return contractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        this.contractVehicleId = contractVehicleId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
