package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.VehicleDocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class VehicleDocument {
    private String vehicleDocumentId;
    private VehicleDocumentType vehicleDocumentType;
    private String registeredLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date registeredDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiryDate;
    private List<VehicleDocumentImage> imageLink;

    public String getVehicleDocumentId() {
        return vehicleDocumentId;
    }

    public void setVehicleDocumentId(String vehicleDocumentId) {
        this.vehicleDocumentId = vehicleDocumentId;
    }

    public VehicleDocumentType getVehicleDocumentType() {
        return vehicleDocumentType;
    }

    public void setVehicleDocumentType(VehicleDocumentType vehicleDocumentType) {
        this.vehicleDocumentType = vehicleDocumentType;
    }

    public String getRegisteredLocation() {
        return registeredLocation;
    }

    public void setRegisteredLocation(String registeredLocation) {
        this.registeredLocation = registeredLocation;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<VehicleDocumentImage> getImageLink() {
        return imageLink;
    }

    public void setImageLink(List<VehicleDocumentImage> imageLink) {
        this.imageLink = imageLink;
    }
}
