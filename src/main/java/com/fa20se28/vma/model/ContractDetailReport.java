package com.fa20se28.vma.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContractDetailReport {
    private String contractId;
    private LocalDate signedDate;
    private String signedLocation;
    private String address;
    private String phoneNumber;
    private String fax;
    private String taxCode;
    private String accountNumber;
    private String customerName;
    private int numberOfPassengers;
    private float contractValue;
    private String departureLocation;
    private LocalDateTime departureTime;
    private String destinationLocation;
    private LocalDateTime destinationTime;
    private int numberOfVehicles;
    private LocalDate durationFrom;
    private LocalDate durationTo;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public float getContractValue() {
        return contractValue;
    }

    public void setContractValue(float contractValue) {
        this.contractValue = contractValue;
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

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
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
}
