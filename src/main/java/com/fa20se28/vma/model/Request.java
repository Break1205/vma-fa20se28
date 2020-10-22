package com.fa20se28.vma.model;

import java.util.Date;

public class Request {
    private Long requestId;
    private String userId;
    private Long requestStatusId;
    private Date requestDate;
    private String description;
    private boolean isDeleted;

    public Request(String userId,
                   Long requestStatusId,
                   Date requestDate,
                   String description,
                   boolean isDeleted) {
        this.userId = userId;
        this.requestStatusId = requestStatusId;
        this.requestDate = requestDate;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(Long requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
