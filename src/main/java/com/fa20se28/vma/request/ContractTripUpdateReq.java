package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ContractTripUpdateReq {
    private int contractTripId;
    private String departureLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    private String destinationLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime destinationTime;

    public int getContractTripId() {
        return contractTripId;
    }

    public void setContractTripId(int contractTripId) {
        this.contractTripId = contractTripId;
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
}
