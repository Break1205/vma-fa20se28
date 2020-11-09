package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class VehicleReq {
    private String vehicleId;
    private int vehicleTypeId;
    private int brandId;
    private String ownerId;
    private int seats;
    private String imageLink;
    private String model;
    private String origin;
    private String chassisNumber;
    private String engineNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date yearOfManufacture;
    private float distanceDriven;
    private List<VehicleDocumentReq> vehicleDocuments;

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

    public List<VehicleDocumentReq> getVehicleDocuments() {
        return vehicleDocuments;
    }

    public void setVehicleDocuments(List<VehicleDocumentReq> vehicleDocuments) {
        this.vehicleDocuments = vehicleDocuments;
    }
}
