package com.fa20se28.vma.request;

public class DriverPageReq {
    private String userId;
    private String name;
    private String phoneNumber;
    private Long userStatusId;
    private int page;

    public DriverPageReq(String userId, String name, String phoneNumber, Long userStatusId, int page) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userStatusId = userStatusId;
        this.page = page;
    }

    public DriverPageReq(String userId, String name, String phoneNumber, Long userStatusId) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userStatusId = userStatusId;
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

    public Long getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Long userStatusId) {
        this.userStatusId = userStatusId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
