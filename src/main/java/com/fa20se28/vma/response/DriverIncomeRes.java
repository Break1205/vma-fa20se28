package com.fa20se28.vma.response;

import com.fa20se28.vma.model.DriverIncome;

import java.util.List;

public class DriverIncomeRes {
    private List<DriverIncome> driverIncomes;
    private float earnedValue;

    public List<DriverIncome> getDriverIncomes() {
        return driverIncomes;
    }

    public void setDriverIncomes(List<DriverIncome> driverIncomes) {
        this.driverIncomes = driverIncomes;
    }

    public float getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(float earnedValue) {
        this.earnedValue = earnedValue;
    }
}
