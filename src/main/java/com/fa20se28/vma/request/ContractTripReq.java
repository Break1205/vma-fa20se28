package com.fa20se28.vma.request;

import java.time.LocalDateTime;
import java.util.List;

public class ContractTripReq {
    private int contractDetailId;
    private String departureLocation;
    private LocalDateTime departureTime;
    private String destinationLocation;
    private LocalDateTime destinationTime;
    private List<ContractTripScheduleReq> locations;
    private String assignedVehicle;

    public int getContractDetailId() {
        return contractDetailId;
    }

    public void setContractDetailId(int contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public LocalDateTime getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(LocalDateTime destinationTime) {
        this.destinationTime = destinationTime;
    }

    public List<ContractTripScheduleReq> getLocations() {
        return locations;
    }

    public void setLocations(List<ContractTripScheduleReq> locations) {
        this.locations = locations;
    }

    public String getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(String assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}
