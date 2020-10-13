package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;

import java.util.List;

public interface DriverComponent {
    DriverDetail findDriverById(String userId);

    List<Driver> findDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page);

    int findTotalDrivers();

    int findTotalDriversWhenFilter(String userId, String name, String phoneNumber, Long userStatusId);
}
