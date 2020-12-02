package com.fa20se28.vma.request;

import java.util.List;

public class ContractTripScheduleUpdateReq {
    private int contractTripId;
    private List<ContractTripScheduleReq> locations;

    public int getContractTripId() {
        return contractTripId;
    }

    public void setContractTripId(int contractTripId) {
        this.contractTripId = contractTripId;
    }

    public List<ContractTripScheduleReq> getLocations() {
        return locations;
    }

    public void setLocations(List<ContractTripScheduleReq> locations) {
        this.locations = locations;
    }
}
