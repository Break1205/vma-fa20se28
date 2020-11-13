package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VehicleUpdateReq {
    private String vehicleId;
    private int vehicleTypeId;
    private int brandId;
    private String ownerId;
    private VehicleStatus vehicleStatus;
    private int seats;
    private String imageLink;
    private String model;
    private String origin;
    private String chassisNumber;
    private String engineNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date yearOfManufacture;
    private float distanceDriven;

    public VehicleUpdateReq(String vehicleId, int vehicleTypeId, int brandId, String ownerId, VehicleStatus vehicleStatus, int seats, String imageLink, String model, String origin, String chassisNumber, String engineNumber, Date yearOfManufacture, float distanceDriven) {
        this.vehicleId = vehicleId;
        this.vehicleTypeId = vehicleTypeId;
        this.brandId = brandId;
        this.ownerId = ownerId;
        this.vehicleStatus = vehicleStatus;
        this.seats = seats;
        this.imageLink = imageLink;
        this.model = model;
        this.origin = origin;
        this.chassisNumber = chassisNumber;
        this.engineNumber = engineNumber;
        this.yearOfManufacture = yearOfManufacture;
        this.distanceDriven = distanceDriven;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public float getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(float distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
