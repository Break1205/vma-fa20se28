package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.UserStatus;

public class ContributorPageReq {
    private String userId;
    private String fullName;
    private String phoneNumber;
    private UserStatus userStatus;
    private Long min;
    private Long max;
    private int page;

    public ContributorPageReq(String userId,
                              String fullName,
                              String phoneNumber,
                              UserStatus userStatus,
                              Long min,
                              Long max,
                              int page) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.userStatus = userStatus;
        this.min = min;
        this.max = max;
        this.page = page;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
