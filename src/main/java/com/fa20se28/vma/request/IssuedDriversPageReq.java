package com.fa20se28.vma.request;

public class IssuedDriversPageReq {
    private String fullName;
    private String phoneNumber;
    private String vehicleId;
    private int page;

    public IssuedDriversPageReq(String name, String phoneNumber, String vehicleId, int page) {
        this.fullName = name;
        this.phoneNumber = phoneNumber;
        this.vehicleId = vehicleId;
        this.page = page;
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
