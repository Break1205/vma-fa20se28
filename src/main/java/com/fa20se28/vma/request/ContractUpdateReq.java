package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ContractUpdateReq {
    private int contractId;
    private String contractOwnerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate signedDate;
    private String signedLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate durationTo;
    private int estimatedPassengerCount;
    private int estimatedVehicleCount;
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
