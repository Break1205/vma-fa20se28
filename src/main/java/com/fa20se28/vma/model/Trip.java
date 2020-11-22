package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractVehicleStatus;

import java.util.List;

public class Trip {
    private int contractVehicleId;
    private int contractId;
    private List<ContractTrip> contractTrips;
    private ContractVehicleStatus contractVehicleStatus;

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

    public List<ContractTrip> getContractTrips() {
        return contractTrips;
    }

    public void setContractTrips(List<ContractTrip> contractTrips) {
        this.contractTrips = contractTrips;
    }

    public ContractVehicleStatus getContractVehicleStatus() {
        return contractVehicleStatus;
    }

    public void setContractVehicleStatus(ContractVehicleStatus contractVehicleStatus) {
        this.contractVehicleStatus = contractVehicleStatus;
    }
}
