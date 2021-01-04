package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class ContractVehicle {
    private int contractVehicleId;
    private int contractTripId;
    private int issuedVehicleId;
    private ContractVehicleStatus contractVehicleStatus;
    private int far;
    private String backupLocation;
    private float driverMoney;
    private float contributorMoney;

    public ContractVehicle(int contractTripId,
                           int issuedVehicleId,
                           ContractVehicleStatus contractVehicleStatus,
                           int far,
                           String backupLocation,
                           float driverMoney,
                           float contributorMoney) {
        this.contractTripId = contractTripId;
        this.issuedVehicleId = issuedVehicleId;
        this.contractVehicleStatus = contractVehicleStatus;
        this.far = far;
        this.backupLocation = backupLocation;
        this.driverMoney = driverMoney;
        this.contributorMoney = contributorMoney;
    }

    public ContractVehicle() {
    }

    public int getContractVehicleId() {
        return contractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        this.contractVehicleId = contractVehicleId;
    }

    public int getContractTripId() {
        return contractTripId;
    }

    public void setContractTripId(int contractTripId) {
        this.contractTripId = contractTripId;
    }

    public int getIssuedVehicleId() {
        return issuedVehicleId;
    }

    public void setIssuedVehicleId(int issuedVehicleId) {
        this.issuedVehicleId = issuedVehicleId;
    }

    public ContractVehicleStatus getContractVehicleStatus() {
        return contractVehicleStatus;
    }

    public void setContractVehicleStatus(ContractVehicleStatus contractVehicleStatus) {
        this.contractVehicleStatus = contractVehicleStatus;
    }

    public int getFar() {
        return far;
    }

    public void setFar(int far) {
        this.far = far;
    }

    public String getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(String backupLocation) {
        this.backupLocation = backupLocation;
    }

    public float getDriverMoney() {
        return driverMoney;
    }

    public void setDriverMoney(float driverMoney) {
        this.driverMoney = driverMoney;
    }

    public float getContributorMoney() {
        return contributorMoney;
    }

    public void setContributorMoney(float contributorMoney) {
        this.contributorMoney = contributorMoney;
    }
}
