package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.VehicleDocumentType;

import java.time.LocalDate;
import java.util.List;

public class VehicleDocument {
    private String vehicleDocumentId;
    private VehicleDocumentType vehicleDocumentType;
    private String registeredLocation;
    private LocalDate registeredDate;
    private LocalDate expiryDate;
    private List<VehicleDocumentImage> imageLinks;

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

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<VehicleDocumentImage> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<VehicleDocumentImage> imageLinks) {
        this.imageLinks = imageLinks;
    }
}
