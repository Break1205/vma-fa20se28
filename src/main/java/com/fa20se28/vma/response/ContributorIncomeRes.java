package com.fa20se28.vma.response;

import com.fa20se28.vma.model.ContributorIncome;

import java.util.List;

public class ContributorIncomeRes {
    private List<ContributorIncome> contributorIncomes;

    public List<ContributorIncome> getContributorIncomes() {
        return contributorIncomes;
    }

    public void setContributorIncomes(List<ContributorIncome> contributorIncomes) {
        this.contributorIncomes = contributorIncomes;
    }
}
