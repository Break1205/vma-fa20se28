package com.fa20se28.vma.request;

public class ReportIssueReq {
    private String description;
    private String vehicleId;
    private String contractTripId;
    private String coordinates;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getContractTripId() {
        return contractTripId;
    }

    public void setContractTripId(String contractTripId) {
        this.contractTripId = contractTripId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

}
