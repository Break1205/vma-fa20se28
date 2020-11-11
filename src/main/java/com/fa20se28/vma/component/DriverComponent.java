package com.fa20se28.vma.component;

import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.IssuedDriversPageReq;
import com.fa20se28.vma.response.DriverRes;
import com.fa20se28.vma.response.IssuedDriversRes;

import java.util.List;

public interface DriverComponent {
    DriverDetail findDriverById(String userId);

    List<DriverRes> findDrivers(DriverPageReq driverPageReq);

    int findTotalDrivers();

    int findTotalDriversWhenFiltering(DriverPageReq driverPageReq);

    List<IssuedDriversRes> findIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq);

    int findTotalIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq);
}
