package com.fa20se28.vma.service;

import com.fa20se28.vma.response.VehiclePageRes;
import org.springframework.stereotype.Service;

@Service
public interface VehicleService {
    int getTotal();

    VehiclePageRes getVehicles();
}
