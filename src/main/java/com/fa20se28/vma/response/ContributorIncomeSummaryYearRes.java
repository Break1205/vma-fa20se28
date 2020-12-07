package com.fa20se28.vma.response;

import java.util.List;

public class ContributorIncomeSummaryYearRes {
    private Integer year;
    private List<ContributorIncomeSummaryMonthRes> contributorIncomeSummaryMonthResList;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<ContributorIncomeSummaryMonthRes> getContributorIncomeSummaryMonthResList() {
        return contributorIncomeSummaryMonthResList;
    }

    public void setContributorIncomeSummaryMonthResList(List<ContributorIncomeSummaryMonthRes> contributorIncomeSummaryMonthResList) {
        this.contributorIncomeSummaryMonthResList = contributorIncomeSummaryMonthResList;
    }
}
