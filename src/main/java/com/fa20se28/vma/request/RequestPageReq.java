package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.enums.Sort;

public class RequestPageReq {
    private String userId;
    private RequestType requestType;
    private RequestStatus requestStatus;
    private String fromDate;
    private String toDate;
    private int page;
    private Sort sort;

    public RequestPageReq(String userId, RequestType requestType, RequestStatus requestStatus, String fromDate, String toDate, int page, Sort sort) {
        this.userId = userId;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.page = page;
        this.sort = sort;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
