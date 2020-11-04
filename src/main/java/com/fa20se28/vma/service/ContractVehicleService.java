package com.fa20se28.vma.service;

import com.fa20se28.vma.request.ContractVehiclePassengerReq;
import com.fa20se28.vma.response.PassengerRes;

public interface ContractVehicleService {
    PassengerRes getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);
}
