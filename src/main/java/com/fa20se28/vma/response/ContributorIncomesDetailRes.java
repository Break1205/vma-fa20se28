package com.fa20se28.vma.response;

import com.fa20se28.vma.model.ContributorIncomesDetail;

import java.util.List;

public class ContributorIncomesDetailRes {
    List<ContributorIncomesDetail> contributorIncomesDetails;

    public List<ContributorIncomesDetail> getContributorIncomesDetails() {
        return contributorIncomesDetails;
    }

    public void setContributorIncomesDetails(List<ContributorIncomesDetail> contributorIncomesDetails) {
        this.contributorIncomesDetails = contributorIncomesDetails;
    }
}
