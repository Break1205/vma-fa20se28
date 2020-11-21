package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ContractReq {
    private String contractOwnerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date signedDate;
    private String signedLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationTo;
    private int estimatedPassengerCount;
    private int estimatedVehicleCount;
    private List<ContractTripReq> trips;
    private float totalPrice;
    private boolean isRoundTrip;
    private String otherInformation;

    public String getContractOwnerId() {
        return contractOwnerId;
    }

    public void setContractOwnerId(String contractOwnerId) {
        this.contractOwnerId = contractOwnerId;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public String getSignedLocation() {
        return signedLocation;
    }

    public void setSignedLocation(String signedLocation) {
        this.signedLocation = signedLocation;
    }

    public Date getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(Date durationFrom) {
        this.durationFrom = durationFrom;
    }

    public Date getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(Date durationTo) {
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
}
