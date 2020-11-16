package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class ContractVehicleStatusUpdateReq {
    private int contractId;
    private String vehicleId;
    private ContractVehicleStatus vehicleStatus;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
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
