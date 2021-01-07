package com.fa20se28.vma.model;

import java.util.List;

public class ContributorEarnedAndEstimatedIncome {
    private float estimated;
    private float earned;
    private List<ContributorIncome> contributorIncomes;

    public float getEstimated() {
        return estimated;
    }

    public void setEstimated(float estimated) {
        this.estimated = estimated;
    }

    public float getEarned() {
        return earned;
    }

    public void setEarned(float earned) {
        this.earned = earned;
    }

    public List<ContributorIncome> getContributorIncomesDetails() {
        return contributorIncomes;
    }

    public void setContributorIncomesDetails(List<ContributorIncome> contributorIncomes) {
        this.contributorIncomes = contributorIncomes;
    }
}
