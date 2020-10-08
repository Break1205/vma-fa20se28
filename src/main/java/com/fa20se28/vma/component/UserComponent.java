package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.response.DriverPageRes;

import java.util.List;

public interface UserComponent {
    User findDriverById (Long userId);

    List<Driver> findDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page);

    int findTotalUsers(Long roleId);
}
