package com.fa20se28.vma.model;

import java.util.Date;
import java.util.List;

public class UserDocument {
    private String userDocumentId;
    private String userDocumentType;
    private String userId;
    private String registerLocation;
    private Date registerDate;
    private Date expiryDate;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegisterLocation() {
        return registerLocation;
    }

    public void setRegisterLocation(String registerLocation) {
        this.registerLocation = registerLocation;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<DocumentImage> getDocumentImages() {
        return documentImages;
    }

    public void setDocumentImages(List<DocumentImage> documentImages) {
        this.documentImages = documentImages;
    }
}
