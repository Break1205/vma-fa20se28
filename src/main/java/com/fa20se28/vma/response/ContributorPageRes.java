package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Contributor;

import java.util.List;

public class ContributorPageRes {
    private int totalContributor;
    private List<Contributor> contributorList;

    public int getTotalContributor() {
        return totalContributor;
    }

    public void setTotalContributor(int totalContributor) {
        this.totalContributor = totalContributor;
    }

    public List<Contributor> getContributorList() {
        return contributorList;
    }

    public void setContributorList(List<Contributor> contributorList) {
        this.contributorList = contributorList;
    }
}
