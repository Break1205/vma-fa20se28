package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class VehicleValueUpdateReq {
    private int vehicleValueId;
    private float value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public VehicleValueUpdateReq(int vehicleValueId, float value, LocalDate startDate, LocalDate endDate) {
        this.vehicleValueId = vehicleValueId;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
}
