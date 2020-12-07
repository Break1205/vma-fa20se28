package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.model.ContributorEarnedAndEstimatedIncome;

public class ContributorIncomeSummaryMonthRes {
    private ContributorEarnedAndEstimatedIncome contributorEarnedAndEstimatedIncome;
    private Quarter quarter;

    public ContributorEarnedAndEstimatedIncome getContributorEarnedAndEstimatedIncome() {
        return contributorEarnedAndEstimatedIncome;
    }

    public void setContributorEarnedAndEstimatedIncome(ContributorEarnedAndEstimatedIncome contributorEarnedAndEstimatedIncome) {
        this.contributorEarnedAndEstimatedIncome = contributorEarnedAndEstimatedIncome;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }
}
