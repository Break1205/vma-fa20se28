package com.fa20se28.vma.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class DriverReq {
    @NotNull(message = "This field is required")
    @Size(max = 9, min = 9)
    private String userId;
    @NotNull(message = "This field is required")
    @Size(max = 50)
    private String fullName;
    @NotNull(message = "This field is required")
    @Size(max = 100)
    private String address;
    @NotNull(message = "This field is required")
    @Size(max = 11, min = 10)
    private String phoneNumber;
    @NotNull(message = "This field is required")
    private int gender;
    @NotNull(message = "This field is required")
    private String dateOfBirth;
    private String imageLink;
    @NotNull(message = "This field is required")
    private Float baseSalary;
    @NotNull(message = "This field is required")
    private int userStatusId;
    private List<UserDocumentReq> userDocumentReqList;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int isGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public int getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(int userStatusId) {
        this.userStatusId = userStatusId;
    }

    public List<UserDocumentReq> getUserDocumentReqList() {
        return userDocumentReqList;
    }

    public void setUserDocumentReqList(List<UserDocumentReq> userDocumentReqList) {
        this.userDocumentReqList = userDocumentReqList;
    }
}
