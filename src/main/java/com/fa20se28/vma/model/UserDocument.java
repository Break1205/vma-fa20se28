package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.UserDocumentType;

import java.time.LocalDate;
import java.util.List;

public class UserDocument {
    private String userDocumentId;
    private UserDocumentType userDocumentType;
    private String registeredLocation;
    private LocalDate registeredDate;
    private LocalDate expiryDate;
    private String otherInformation;
    private List<UserDocumentImage> userDocumentImages;

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public UserDocumentType getUserDocumentType() {
        return userDocumentType;
    }

    public void setUserDocumentType(UserDocumentType userDocumentType) {
        this.userDocumentType = userDocumentType;
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

    public List<UserDocumentImage> getUserDocumentImages() {
        return userDocumentImages;
    }

    public void setUserDocumentImages(List<UserDocumentImage> userDocumentImages) {
        this.userDocumentImages = userDocumentImages;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }
}
