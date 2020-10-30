package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.UserDocumentType;

import java.time.LocalDate;
import java.util.List;

public class UserDocument {
    private String userDocumentId;
    private UserDocumentType userDocumentType;
    private String registerLocation;
    private LocalDate registerDate;
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

    public String getRegisterLocation() {
        return registerLocation;
    }

    public void setRegisterLocation(String registerLocation) {
        this.registerLocation = registerLocation;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<UserDocumentImage> getDocumentImages() {
        return userDocumentImages;
    }

    public void setDocumentImages(List<UserDocumentImage> userDocumentImages) {
        this.userDocumentImages = userDocumentImages;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }
}
