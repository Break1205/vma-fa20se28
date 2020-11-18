package com.fa20se28.vma.response;

import com.fa20se28.vma.model.RevenueExpense;

import java.util.List;

public class RevenueExpenseReportRes {
    private List<RevenueExpense> revenueExpenses;

    public List<RevenueExpense> getRevenueExpenses() {
        return revenueExpenses;
    }

    public void setRevenueExpenses(List<RevenueExpense> revenueExpenses) {
        this.revenueExpenses = revenueExpenses;
    }
}
