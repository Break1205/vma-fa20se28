package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ContractTripReq {
    private String departureLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    private String destinationLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime destinationTime;
    private List<ContractTripScheduleReq> locations;

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
}
