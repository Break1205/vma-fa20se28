package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractStatus;

import java.time.LocalDate;
import java.util.List;

public class Schedule {
    private String contractId;
    private LocalDate date;
    private String departureLocation;
    private String destinationLocation;
    private float contractValue;
    private ContractStatus contractStatus;
    private List<ScheduleDetail> details;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public float getContractValue() {
        return contractValue;
    }

    public void setContractValue(float contractValue) {
        this.contractValue = contractValue;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public List<ScheduleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ScheduleDetail> details) {
        this.details = details;
    }
}
