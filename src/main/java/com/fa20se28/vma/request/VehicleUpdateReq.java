package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VehicleUpdateReq {
    private String vehicleId;
    private int vehicleTypeId;
    private int brandId;
    private String ownerId;
    private int seatsId;
    private String imageLink;
    private String model;
    private String origin;
    private String chassisNumber;
    private String engineNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private String yearOfManufacture;
    private float distanceDriven;
    private VehicleValueReq value;

    public VehicleUpdateReq(String vehicleId, int vehicleTypeId, int brandId, String ownerId, int seatsId, String imageLink, String model, String origin, String chassisNumber, String engineNumber, String yearOfManufacture, float distanceDriven) {
        this.vehicleId = vehicleId;
        this.vehicleTypeId = vehicleTypeId;
        this.brandId = brandId;
        this.ownerId = ownerId;
        this.seatsId = seatsId;
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

    public int getSeatsId() {
        return seatsId;
    }

    public void setSeatsId(int seatsId) {
        this.seatsId = seatsId;
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

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public float getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(float distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    public VehicleValueReq getValue() {
        return value;
    }

    public void setValue(VehicleValueReq value) {
        this.value = value;
    }
}
