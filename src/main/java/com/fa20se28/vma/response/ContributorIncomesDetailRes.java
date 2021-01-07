package com.fa20se28.vma.response;

import com.fa20se28.vma.model.ContributorIncome;

import java.util.List;

public class ContributorIncomesDetailRes {
    List<ContributorIncome> contributorIncomes;

    public List<ContributorIncome> getContributorIncomesDetails() {
        return contributorIncomes;
    }

    public void setContributorIncomesDetails(List<ContributorIncome> contributorIncomes) {
        this.contributorIncomes = contributorIncomes;
    }
}
