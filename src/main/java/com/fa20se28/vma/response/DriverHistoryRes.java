package com.fa20se28.vma.response;

import com.fa20se28.vma.model.DriverHistory;

import java.util.List;

public class DriverHistoryRes {
    private List<DriverHistory> driverHistory;

    public DriverHistoryRes(List<DriverHistory> driverHistory) {
        this.driverHistory = driverHistory;
    }

    public List<DriverHistory> getDriverHistory() {
        return driverHistory;
    }

    public void setDriverHistory(List<DriverHistory> driverHistory) {
        this.driverHistory = driverHistory;
    }
}
