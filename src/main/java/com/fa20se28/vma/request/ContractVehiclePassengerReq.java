package com.fa20se28.vma.request;

import java.util.List;

public class ContractVehiclePassengerReq {
    private int contractVehicleId;
    private List<PassengerReq> passengerList;

    public int getContractVehicleId() {
        return contractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        this.contractVehicleId = contractVehicleId;
    }

    public List<PassengerReq> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<PassengerReq> passengerList) {
        this.passengerList = passengerList;
    }
}
