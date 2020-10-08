package com.fa20se28.vma.component;

import com.fa20se28.vma.model.User;
import com.fa20se28.vma.response.DriverPageRes;

public interface UserComponent {
    public User findDriverById (Long userId);

    public DriverPageRes findDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page);
}
