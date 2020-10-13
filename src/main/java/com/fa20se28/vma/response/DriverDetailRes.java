package com.fa20se28.vma.response;

import com.fa20se28.vma.model.UserDocument;

import java.util.Date;
import java.util.List;

public class DriverDetailRes {
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String vehicleId;
    private String userStatusName;
    private boolean gender;
    private Date dateOfBirth;
    private String address;
    private String imageLink;
    private Float baseSalary;
    private List<UserDocument> userDocumentList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public List<UserDocument> getUserDocumentList() {
        return userDocumentList;
    }

    public void setUserDocumentList(List<UserDocument> userDocumentList) {
        this.userDocumentList = userDocumentList;
    }
}
