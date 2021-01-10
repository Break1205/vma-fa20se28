package com.fa20se28.vma.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class VehicleContract {
    private String vehicleId;
    private String model;
    private VehicleType vehicleType;
    private int seats;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private LocalDate yearOfManufacture;
    private List<SeatPrice> prices;
    private float currentEarnedValue;
    private float expectedValue;

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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public LocalDate getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(LocalDate yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public float getCurrentEarnedValue() {
        return currentEarnedValue;
    }

    public void setCurrentEarnedValue(float currentEarnedValue) {
        this.currentEarnedValue = currentEarnedValue;
    }

    public float getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(float expectedValue) {
        this.expectedValue = expectedValue;
    }

    public List<SeatPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<SeatPrice> prices) {
        this.prices = prices;
    }
}
