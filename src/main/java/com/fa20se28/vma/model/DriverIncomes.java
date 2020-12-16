package com.fa20se28.vma.model;

public class DriverIncomes {
    private String userId;
    private String contractId;
    private String contractDetailId;
    private String vehicleId;
    private float driverEarned;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public float getDriverEarned() {
        return driverEarned;
    }

    public void setDriverEarned(float driverEarned) {
        this.driverEarned = driverEarned;
    }

    public String getContractDetailId() {
        return contractDetailId;
    }

    public void setContractDetailId(String contractDetailId) {
        this.contractDetailId = contractDetailId;
    }
}
