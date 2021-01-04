package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class ContractVehicle {
    private int contractVehicleId;
    private int contractTripId;
    private int issuedVehicleId;
    private ContractVehicleStatus contractVehicleStatus;

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
}
