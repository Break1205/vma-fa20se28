package com.fa20se28.vma.request;

public class ContractVehicleReq {
    private int contractId;
    private int issuedVehicleId;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getIssuedVehicleId() {
        return issuedVehicleId;
    }

    public void setIssuedVehicleId(int issuedVehicleId) {
        this.issuedVehicleId = issuedVehicleId;
    }
}
