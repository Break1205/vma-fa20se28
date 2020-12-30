package com.fa20se28.vma.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class VehicleDetail extends Vehicle {
    private Brand brand;
    private UserBasic owner;
    private String imageLink;
    private String origin;
    private String chassisNumber;
    private String engineNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private LocalDate yearOfManufacture;
    private LocalDate dateOfRegistration;
    private UserBasic assignedDriver;
    private List<VehicleValue> values;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public UserBasic getOwner() {
        return owner;
    }

    public void setOwner(UserBasic owner) {
        this.owner = owner;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public LocalDate getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(LocalDate yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public UserBasic getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(UserBasic assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public List<VehicleValue> getValues() {
        return values;
    }

    public void setValues(List<VehicleValue> values) {
        this.values = values;
    }
}
