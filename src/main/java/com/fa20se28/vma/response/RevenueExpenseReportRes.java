package com.fa20se28.vma.response;

import com.fa20se28.vma.model.RevenueExpense;

import java.util.List;

public class RevenueExpenseReportRes {
    private List<RevenueExpense> revenueExpenses;
    private float totalRevenue;
    private float totalExpense;

    public float getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public float getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(float totalExpense) {
        this.totalExpense = totalExpense;
    }

    public List<RevenueExpense> getRevenueExpenses() {
        return revenueExpenses;
    }

    public void setRevenueExpenses(List<RevenueExpense> revenueExpenses) {
        this.revenueExpenses = revenueExpenses;
    }
}
