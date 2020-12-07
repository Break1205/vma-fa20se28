package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.Quarter;

public class DriverIncomeSummaryMonthRes {
    private DriverIncomeRes driverIncomeRes;
    private Quarter quarter;

    public DriverIncomeRes getDriverIncomeRes() {
        return driverIncomeRes;
    }

    public void setDriverIncomeRes(DriverIncomeRes driverIncomeRes) {
        this.driverIncomeRes = driverIncomeRes;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }
}
