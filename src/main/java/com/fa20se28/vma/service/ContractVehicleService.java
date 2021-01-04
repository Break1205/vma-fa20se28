package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.response.VehicleContractRes;

public interface ContractVehicleService {
    PassengerRes getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);

    void assignVehicleForContract(ContractVehicleReq contractVehicleReq);

    ContractVehicleRes getContractVehiclesByContractTripId(int contractId);

    ContractVehicleStatus getVehicleStatus(int contractTripId, int issuedVehicleId);

    void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq);

    TripListRes getTrips(TripListReq tripListReq, int page);

    TripListRes getCurrentTrip(int issuedVehicleId);

    void startTrip(TripReq tripReq);

    void endTrip(TripReq tripReq);

    VehicleContractRes getAvailableVehicles(VehicleContractReq vehicleContractReq, int pageNum, int viewOption);

    int getTotalAvailableVehicles(VehicleContractReq vehicleContractReq, int viewOption);
}
