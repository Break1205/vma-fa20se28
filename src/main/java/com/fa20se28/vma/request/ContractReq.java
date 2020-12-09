package com.fa20se28.vma.request;

import java.time.LocalDate;
import java.util.List;

public class ContractReq {
    private String contractOwnerId;
    private LocalDate signedDate;
    private String signedLocation;
    private LocalDate durationFrom;
    private LocalDate durationTo;
    private int estimatedPassengerCount;
    private int estimatedVehicleCount;
    private List<ContractTripReq> trips;
    private float totalPrice;
    private boolean isRoundTrip;
    private String otherInformation;
    private List<String> assignedVehicles;

    public String getContractOwnerId() {
        return contractOwnerId;
    }

    public void setContractOwnerId(String contractOwnerId) {
        this.contractOwnerId = contractOwnerId;
    }

    public LocalDate getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDate signedDate) {
        this.signedDate = signedDate;
    }

    public String getSignedLocation() {
        return signedLocation;
    }

    public void setSignedLocation(String signedLocation) {
        this.signedLocation = signedLocation;
    }

    public LocalDate getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(LocalDate durationFrom) {
        this.durationFrom = durationFrom;
    }

    public LocalDate getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(LocalDate durationTo) {
        this.durationTo = durationTo;
    }

    public int getEstimatedPassengerCount() {
        return estimatedPassengerCount;
    }

    public void setEstimatedPassengerCount(int estimatedPassengerCount) {
        this.estimatedPassengerCount = estimatedPassengerCount;
    }

    public int getEstimatedVehicleCount() {
        return estimatedVehicleCount;
    }

    public void setEstimatedVehicleCount(int estimatedVehicleCount) {
        this.estimatedVehicleCount = estimatedVehicleCount;
    }

    public List<ContractTripReq> getTrips() {
        return trips;
    }

    public void setTrips(List<ContractTripReq> trips) {
        this.trips = trips;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public List<String> getAssignedVehicles() {
        return assignedVehicles;
    }

    public void setAssignedVehicles(List<String> assignedVehicles) {
        this.assignedVehicles = assignedVehicles;
    }
}
