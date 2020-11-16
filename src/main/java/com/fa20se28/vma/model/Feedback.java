package com.fa20se28.vma.model;

import java.time.LocalDateTime;

public class Feedback {
    private int feedbackId;
    private UserBasic contributor;
    private String vehicleId;
    private UserBasic driver;
    private LocalDateTime createDate;
    private int rate;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public UserBasic getContributor() {
        return contributor;
    }

    public void setContributor(UserBasic contributor) {
        this.contributor = contributor;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UserBasic getDriver() {
        return driver;
    }

    public void setDriver(UserBasic driver) {
        this.driver = driver;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
