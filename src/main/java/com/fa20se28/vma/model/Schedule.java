package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractVehicleStatus;

import java.time.LocalDate;

public class Schedule {
    private String vehicleId;
    private String departureLocation;
    private LocalDate departureTime;
    private String destinationLocation;
    private LocalDate destinationTime;
    private ContractVehicleStatus contractVehicleStatus;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public LocalDate getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(LocalDate destinationTime) {
        this.destinationTime = destinationTime;
    }

    public ContractVehicleStatus getContractVehicleStatus() {
        return contractVehicleStatus;
    }

    public void setContractVehicleStatus(ContractVehicleStatus contractVehicleStatus) {
        this.contractVehicleStatus = contractVehicleStatus;
    }
}
