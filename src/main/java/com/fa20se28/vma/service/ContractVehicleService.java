package com.fa20se28.vma.service;

import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;

public interface ContractVehicleService {
    PassengerRes getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);

    void assignVehicleForContract (ContractVehicleReq contractVehicleReq);

    ContractVehicleRes getContractVehicles(int contractId);

    void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq);

    void startAndEndTrip(TripReq tripReq);

    TripListRes getTrips(TripListReq tripListReq);
}
