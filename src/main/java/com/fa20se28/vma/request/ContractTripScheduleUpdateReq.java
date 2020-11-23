package com.fa20se28.vma.request;

public class ContractTripScheduleUpdateReq {
    private int locationId;
    private String location;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
