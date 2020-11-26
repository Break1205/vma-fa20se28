package com.fa20se28.vma.response;

import com.fa20se28.vma.model.EstimateAndEarnedIncome;

import java.util.Map;

public class ContributorIncomeRes {
    private Map<String, EstimateAndEarnedIncome> contributorEstimateAndEarnedIncomes;

    public Map<String, EstimateAndEarnedIncome> getContributorEstimateAndEarnedIncomes() {
        return contributorEstimateAndEarnedIncomes;
    }

    public void setContributorEstimateAndEarnedIncomes(Map<String, EstimateAndEarnedIncome> contributorEstimateAndEarnedIncomes) {
        this.contributorEstimateAndEarnedIncomes = contributorEstimateAndEarnedIncomes;
    }
}
