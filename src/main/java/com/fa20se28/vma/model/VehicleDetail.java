package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VehicleDetail {
    private String vehicleId;
    private VehicleType vehicleType;
    private Brand brand;
    private UserBasic owner;
    private VehicleStatus vehicleStatus;
    private int seats;
    private String imageLink;
    private String model;
    private String origin;
    private String chassisNumber;
    private String engineNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date yearOfManufacture;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfRegistration;
    private float distanceDriven;
    private UserBasic assignedDriver;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

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

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public Date getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Date yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public float getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(float distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    public UserBasic getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(UserBasic assignedDriver) {
        this.assignedDriver = assignedDriver;
    }
}
