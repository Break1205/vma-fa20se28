package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.UserDocumentType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UserDocumentDetail {
    private String userDocumentId;
    private String userId;
    private String registeredLocation;
    private LocalDate registeredDate;
    private LocalDate expiryDate;
    private Date createDate;
    private String otherInformation;
    private UserDocumentType userDocumentType;
    private List<UserDocumentImageDetail> userDocumentImageDetailList;

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public UserDocumentType getUserDocumentType() {
        return userDocumentType;
    }

    public void setUserDocumentType(UserDocumentType userDocumentType) {
        this.userDocumentType = userDocumentType;
    }

    public List<UserDocumentImageDetail> getUserDocumentImageDetailList() {
        return userDocumentImageDetailList;
    }

    public void setUserDocumentImageDetailList(List<UserDocumentImageDetail> userDocumentImageDetailList) {
        this.userDocumentImageDetailList = userDocumentImageDetailList;
    }
}
