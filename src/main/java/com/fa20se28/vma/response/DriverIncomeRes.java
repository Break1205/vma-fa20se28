package com.fa20se28.vma.response;

import com.fa20se28.vma.model.DriverIncomes;

import java.util.List;

public class DriverIncomeRes {
    private List<DriverIncomes> driverIncomes;
    private float earnedValue;

    public List<DriverIncomes> getDriverIncomes() {
        return driverIncomes;
    }

    public void setDriverIncomes(List<DriverIncomes> driverIncomes) {
        this.driverIncomes = driverIncomes;
    }

    public float getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(float earnedValue) {
        this.earnedValue = earnedValue;
    }
}
