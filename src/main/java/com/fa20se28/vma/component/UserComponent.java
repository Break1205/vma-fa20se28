package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.User;

import java.util.List;

public interface UserComponent {
    User findDriverById (Long userId);

    List<Driver> findDrivers(int page);

    List<Contributor> findContributors(int page);

    List<Driver> searchDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page);

    List<Contributor> searchContributors(String userId,String name,String phoneNumber,Long totalVehicles,int page);

    int findTotalUsers(Long roleId);
}
