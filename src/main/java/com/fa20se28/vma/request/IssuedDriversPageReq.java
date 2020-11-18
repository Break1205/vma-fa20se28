package com.fa20se28.vma.request;

public class IssuedDriversPageReq {
    private String contributorId;
    private String fullName;
    private String phoneNumber;
    private String vehicleId;
    private int page;

    public IssuedDriversPageReq(String contributorId, String fullName, String phoneNumber, String vehicleId, int page) {
        this.contributorId = contributorId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.vehicleId = vehicleId;
        this.page = page;
    }

    public String getContributorId() {
        return contributorId;
    }

    public void setContributorId(String contributorId) {
        this.contributorId = contributorId;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
