package com.fa20se28.vma.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DriverHistory {
    private UserBasic driver;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date issuedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date returnedDate;

    public UserBasic getDriver() {
        return driver;
    }

    public void setDriver(UserBasic driver) {
        this.driver = driver;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
}
