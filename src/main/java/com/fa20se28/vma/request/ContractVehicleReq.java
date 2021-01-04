package com.fa20se28.vma.request;

public class ContractVehicleReq {
    private int contractTripId;
    private String vehicleId;

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
}
