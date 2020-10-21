package com.fa20se28.vma.request;

public class DriverPageReq {
    private String userId;
    private String fullName;
    private String phoneNumber;
    private Long userStatusId;
    private int page;
    private Long viewOption;

    public DriverPageReq(String userId, String fullName, String phoneNumber, Long userStatusId, int page, Long viewOption) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.userStatusId = userStatusId;
        this.page = page;
        this.viewOption = viewOption;
    }

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

    public Long getViewOption() {
        return viewOption;
    }

    public void setViewOption(Long viewOption) {
        this.viewOption = viewOption;
    }
}
