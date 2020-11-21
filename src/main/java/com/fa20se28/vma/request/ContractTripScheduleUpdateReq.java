package com.fa20se28.vma.request;

public class ContractTripScheduleUpdateReq {
    private int contractDetailScheduleId;
    private String location;

    public int getContractDetailScheduleId() {
        return contractDetailScheduleId;
    }

    public void setContractDetailScheduleId(int contractDetailScheduleId) {
        this.contractDetailScheduleId = contractDetailScheduleId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
