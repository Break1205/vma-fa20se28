package com.fa20se28.vma.model;

import java.util.List;

public class ContributorEarnedAndEstimatedIncome {
    private float estimated;
    private float earned;
    private List<ContributorIncomesDetail> contributorIncomesDetails;

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

    public List<ContributorIncomesDetail> getContributorIncomesDetails() {
        return contributorIncomesDetails;
    }

    public void setContributorIncomesDetails(List<ContributorIncomesDetail> contributorIncomesDetails) {
        this.contributorIncomesDetails = contributorIncomesDetails;
    }
}
