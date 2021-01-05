package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractStatus;

import java.time.LocalDate;
import java.util.List;

public class ContractDetail {
    private int contractId;
    private UserBasic contractOwner;
    private ContractStatus contractStatus;
    private LocalDate signedDate;
    private String signedLocation;
    private LocalDate durationFrom;
    private LocalDate durationTo;
    private List<ContractTrip> trips;
    private int passengerCount;
    private int vehicleCount;
    private boolean isRoundTrip;
    private float totalPrice;
    private String otherInformation;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public UserBasic getContractOwner() {
        return contractOwner;
    }

    public void setContractOwner(UserBasic contractOwner) {
        this.contractOwner = contractOwner;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
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

    public List<ContractTrip> getTrips() {
        return trips;
    }

    public void setTrips(List<ContractTrip> trips) {
        this.trips = trips;
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

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }
}
