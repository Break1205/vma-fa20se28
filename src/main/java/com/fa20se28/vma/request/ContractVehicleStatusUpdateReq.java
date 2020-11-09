package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class ContractVehicleStatusUpdateReq {
    private int contractVehicleId;
    private ContractVehicleStatus vehicleStatus;

    public int getContractVehicleId() {
        return contractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        this.contractVehicleId = contractVehicleId;
    }

    public ContractVehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(ContractVehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
