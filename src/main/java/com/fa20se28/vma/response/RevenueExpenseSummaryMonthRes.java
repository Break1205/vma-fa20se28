package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.Quarter;

public class RevenueExpenseSummaryMonthRes {
    private RevenueExpenseReportRes revenueExpenseReportRes;
    private Quarter quarter;

    public RevenueExpenseReportRes getRevenueExpenseReportRes() {
        return revenueExpenseReportRes;
    }

    public void setRevenueExpenseReportRes(RevenueExpenseReportRes revenueExpenseReportRes) {
        this.revenueExpenseReportRes = revenueExpenseReportRes;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }
}
