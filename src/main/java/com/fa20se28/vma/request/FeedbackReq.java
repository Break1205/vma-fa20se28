package com.fa20se28.vma.request;

public class FeedbackReq {
    private Integer issuedVehicleId;
    private Integer rate;
    private String comment;

    public Integer getIssuedVehicleId() {
        return issuedVehicleId;
    }

    public void setIssuedVehicleId(Integer issuedVehicleId) {
        this.issuedVehicleId = issuedVehicleId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
