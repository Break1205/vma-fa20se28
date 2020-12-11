package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class ContractVehicleStatusUpdateReq {
    private int contractDetailId;
    private String vehicleId;
    private ContractVehicleStatus vehicleStatus;

    public int getContractDetailId() {
        return contractDetailId;
    }

    public void setContractDetailId(int contractDetailId) {
        this.contractDetailId = contractDetailId;
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
