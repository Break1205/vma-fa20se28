package com.fa20se28.vma.component;

import com.fa20se28.vma.request.DriverReq;

public interface UserComponent {
    int createDriver(DriverReq driverReq);

    void deleteUserById(String userId);
}
