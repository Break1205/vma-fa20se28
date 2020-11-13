package com.fa20se28.vma.model;

import java.time.LocalDate;

public class DriverHistory {
    private UserBasic driver;
    private LocalDate issuedDate;
    private LocalDate returnedDate;

    public UserBasic getDriver() {
        return driver;
    }

    public void setDriver(UserBasic driver) {
        this.driver = driver;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
