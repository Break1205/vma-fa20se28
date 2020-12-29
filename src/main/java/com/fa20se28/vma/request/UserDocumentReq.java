package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.UserDocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class UserDocumentReq {
    private int userDocumentId;
    private String userDocumentNumber;
    private UserDocumentType userDocumentType;
    private String registeredLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate registeredDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    private String otherInformation;
    private List<UserDocumentImageReq> userDocumentImages;

    public int getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(int userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public String getUserDocumentNumber() {
        return userDocumentNumber;
    }

    public void setUserDocumentNumber(String userDocumentNumber) {
        this.userDocumentNumber = userDocumentNumber;
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

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public List<UserDocumentImageReq> getUserDocumentImages() {
        return userDocumentImages;
    }

    public void setUserDocumentImages(List<UserDocumentImageReq> userDocumentImages) {
        this.userDocumentImages = userDocumentImages;
    }
}
