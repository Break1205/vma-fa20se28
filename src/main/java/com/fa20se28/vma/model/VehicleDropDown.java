package com.fa20se28.vma.model;

public class VehicleDropDown {
    private String vehicleId;
    private String model;
    private String vehicleTypeName;
    private int seats;

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
}
