package com.fa20se28.vma.response;

import com.fa20se28.vma.model.DriverIncome;

import java.util.List;

public class DriversIncomeRes {
    private List<DriverIncome> driverIncomes;

    public List<DriverIncome> getDriverIncomes() {
        return driverIncomes;
    }

    public void setDriverIncomes(List<DriverIncome> driverIncomes) {
        this.driverIncomes = driverIncomes;
    }
}
