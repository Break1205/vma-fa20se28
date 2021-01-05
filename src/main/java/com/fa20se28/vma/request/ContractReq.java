package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.List;

public class ContractReq {
    private int contractId;
    private String contractOwnerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate signedDate;
    private String signedLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate durationTo;
    private int passengerCount;
    private int vehicleCount;
    @JsonSerialize(as = ContractTripReq.class)
    private List<ContractTripReq> trips;
    private float totalPrice;
    private boolean isRoundTrip;
    private String otherInformation;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

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

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
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
}
