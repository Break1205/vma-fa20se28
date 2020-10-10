package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Contributor;

import java.util.List;

public class ContributorPageRes {
    private List<Contributor> contributorList;

    public List<Contributor> getContributorList() {
        return contributorList;
    }

    public void setContributorList(List<Contributor> contributorList) {
        this.contributorList = contributorList;
    }
}
