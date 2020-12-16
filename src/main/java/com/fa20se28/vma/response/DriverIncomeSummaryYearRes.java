package com.fa20se28.vma.response;

import java.util.List;

public class DriverIncomeSummaryYearRes {
    private Integer year;
    private float totalEarned;
    private List<DriverIncomeSummaryMonthRes> driverIncomeSummaryMonthResList;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<DriverIncomeSummaryMonthRes> getDriverIncomeSummaryMonthResList() {
        return driverIncomeSummaryMonthResList;
    }

    public void setDriverIncomeSummaryMonthResList(List<DriverIncomeSummaryMonthRes> driverIncomeSummaryMonthResList) {
        this.driverIncomeSummaryMonthResList = driverIncomeSummaryMonthResList;
    }

    public float getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(float totalEarned) {
        this.totalEarned = totalEarned;
    }
}
