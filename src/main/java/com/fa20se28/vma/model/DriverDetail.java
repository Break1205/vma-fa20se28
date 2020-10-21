package com.fa20se28.vma.model;

import java.time.LocalDate;
import java.util.List;

public class DriverDetail extends Driver {
    private boolean gender;
    private LocalDate dateOfBirth;
    private String address;
    private String imageLink;
    private Float baseSalary;
    private List<UserDocument> userDocumentList;

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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
