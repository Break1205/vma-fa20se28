package com.fa20se28.vma.model;

public class VehicleStatusCount {
    private String statusName;
    private int statusCount;

    public VehicleStatusCount(String statusName, int statusCount) {
        this.statusName = statusName;
        this.statusCount = statusCount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(int statusCount) {
        this.statusCount = statusCount;
    }
}
