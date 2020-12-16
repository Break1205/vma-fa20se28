package com.fa20se28.vma.response;

import java.util.List;

public class RevenueExpenseSummaryYearRes {
    private Integer year;
    private float totalExpense;
    private float totalRevenue;
    private List<RevenueExpenseSummaryMonthRes> revenueExpenseSummaryMonthResList;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<RevenueExpenseSummaryMonthRes> getRevenueExpenseSummaryMonthResList() {
        return revenueExpenseSummaryMonthResList;
    }

    public void setRevenueExpenseSummaryMonthResList(List<RevenueExpenseSummaryMonthRes> revenueExpenseSummaryMonthResList) {
        this.revenueExpenseSummaryMonthResList = revenueExpenseSummaryMonthResList;
    }

    public float getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(float totalExpense) {
        this.totalExpense = totalExpense;
    }

    public float getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
