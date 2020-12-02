package com.fa20se28.vma.model;

import java.time.LocalDate;

public class VehicleValue {
    private int vehicleValueId;
    private float value;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isDeleted;

    public int getVehicleValueId() {
        return vehicleValueId;
    }

    public void setVehicleValueId(int vehicleValueId) {
        this.vehicleValueId = vehicleValueId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
