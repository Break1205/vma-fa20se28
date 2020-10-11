package com.fa20se28.vma.request;

public class ContributorPageReq {
    private String userId;
    private String name;
    private String phoneNumber;
    private Long min;
    private Long max;
    private int page;


    public ContributorPageReq(String userId, String name, String phoneNumber, Long min, Long max, int page) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.min = min;
        this.max = max;
        this.page = page;
    }

    public ContributorPageReq(String userId, String name, String phoneNumber, Long min, Long max) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.min = min;
        this.max = max;
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
