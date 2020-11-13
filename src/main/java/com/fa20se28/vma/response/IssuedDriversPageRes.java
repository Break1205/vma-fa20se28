package com.fa20se28.vma.response;

import java.util.List;

public class IssuedDriversPageRes {
    private List<IssuedDriversRes> drivers;

    public List<IssuedDriversRes> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<IssuedDriversRes> drivers) {
        this.drivers = drivers;
    }
}
