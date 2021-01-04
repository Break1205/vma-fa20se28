package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class ContractVehicleStatusUpdateReq {
    private int contractTripId;
    private String vehicleId;
    private ContractVehicleStatus vehicleStatus;

    public int getContractTripId() {
        return contractTripId;
    }

    public void setContractTripId(int contractTripId) {
        this.contractTripId = contractTripId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public ContractVehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(ContractVehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
