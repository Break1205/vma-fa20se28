package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.VehicleDocumentType;
import com.fa20se28.vma.model.VehicleDocumentImage;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class VehicleDocumentUpdateReq {
    private String vehicleDocumentId;
    private VehicleDocumentType vehicleDocumentType;
    private String registeredLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate registeredDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    private List<VehicleDocumentImage> imageLinks;

    public VehicleDocumentUpdateReq(String vehicleDocumentId, VehicleDocumentType vehicleDocumentType, String registeredLocation, LocalDate registeredDate, LocalDate expiryDate) {
        this.vehicleDocumentId = vehicleDocumentId;
        this.vehicleDocumentType = vehicleDocumentType;
        this.registeredLocation = registeredLocation;
        this.registeredDate = registeredDate;
        this.expiryDate = expiryDate;
    }

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
