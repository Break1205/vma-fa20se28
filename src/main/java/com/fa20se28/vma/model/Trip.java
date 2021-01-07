package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class Trip {
    private int contractVehicleId;
    private int contractId;
    private ContractTrip contractTrip;
    private ContractVehicleStatus contractVehicleStatus;
    private String backupLocation;

    public int getContractVehicleId() {
        return contractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        this.contractVehicleId = contractVehicleId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public ContractTrip getContractTrip() {
        return contractTrip;
    }

    public void setContractTrip(ContractTrip contractTrip) {
        this.contractTrip = contractTrip;
    }

    public ContractVehicleStatus getContractVehicleStatus() {
        return contractVehicleStatus;
    }

    public void setContractVehicleStatus(ContractVehicleStatus contractVehicleStatus) {
        this.contractVehicleStatus = contractVehicleStatus;
    }

    public String getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(String backupLocation) {
        this.backupLocation = backupLocation;
    }
}
