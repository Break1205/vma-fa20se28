package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractVehicleStatus;

public class TripListReq {
    private int issuedVehicleId;
    private ContractVehicleStatus vehicleStatus;

    public TripListReq(int issuedVehicleId, ContractVehicleStatus vehicleStatus) {
        this.issuedVehicleId = issuedVehicleId;
        this.vehicleStatus = vehicleStatus;
    }

    public int getIssuedVehicleId() {
        return issuedVehicleId;
    }

    public void setIssuedVehicleId(int issuedVehicleId) {
        this.issuedVehicleId = issuedVehicleId;
    }

    public ContractVehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(ContractVehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
