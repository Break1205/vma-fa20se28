package com.fa20se28.vma.model;

import java.time.LocalDate;
import java.util.List;

public class UserDocument {
    private String userDocumentId;
    private String userDocumentType;
    private String registerLocation;
    private LocalDate registerDate;
    private LocalDate expiryDate;
    private String otherInformation;
    private List<DocumentImage> documentImages;

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public String getUserDocumentType() {
        return userDocumentType;
    }

    public void setUserDocumentType(String userDocumentType) {
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

    public List<DocumentImage> getDocumentImages() {
        return documentImages;
    }

    public void setDocumentImages(List<DocumentImage> documentImages) {
        this.documentImages = documentImages;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }
}
