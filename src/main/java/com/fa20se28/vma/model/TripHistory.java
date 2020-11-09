package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TripHistory {
    private int contractId;
    private int contractVehicleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date destinationTime;
    private ContractVehicleStatus contractVehicleStatus;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getContractVehicleId() {
        return contractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        this.contractVehicleId = contractVehicleId;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(Date destinationTime) {
        this.destinationTime = destinationTime;
    }

    public ContractVehicleStatus getContractVehicleStatus() {
        return contractVehicleStatus;
    }

    public void setContractVehicleStatus(ContractVehicleStatus contractVehicleStatus) {
        this.contractVehicleStatus = contractVehicleStatus;
    }
}
