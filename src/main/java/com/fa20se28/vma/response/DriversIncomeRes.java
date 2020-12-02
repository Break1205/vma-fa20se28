package com.fa20se28.vma.response;

import com.fa20se28.vma.model.DriverIncomes;

import java.util.List;

public class DriversIncomeRes {
    private List<DriverIncomes> driverIncomes;

    public List<DriverIncomes> getDriverIncomes() {
        return driverIncomes;
    }

    public void setDriverIncomes(List<DriverIncomes> driverIncomes) {
        this.driverIncomes = driverIncomes;
    }
}
