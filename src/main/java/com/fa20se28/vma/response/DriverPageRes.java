package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Driver;

import java.util.List;

public class DriverPageRes {
    private List<Driver> driverList;

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }
}
