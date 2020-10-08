package com.fa20se28.vma.request;

public class ContributorPageReq {
    private String userId;
    private String name;
    private String phoneNumber;
    private Long totalVehicles;
    private int page;

    public ContributorPageReq(String userId, String name, String phoneNumber, Long totalVehicles, int page) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.totalVehicles = totalVehicles;
        this.page = page;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(Long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
