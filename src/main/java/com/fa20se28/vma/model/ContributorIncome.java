package com.fa20se28.vma.model;

import java.time.LocalDate;

public class ContributorIncome {
    private String ownerId;
    private String vehicleId;
    private LocalDate date;
    private float value;
    private String contractId;
    private String contractTripId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractTripId() {
        return contractTripId;
    }

    public void setContractTripId(String contractTripId) {
        this.contractTripId = contractTripId;
    }
}
