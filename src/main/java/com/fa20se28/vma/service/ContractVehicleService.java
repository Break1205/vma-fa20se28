package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.response.VehicleRecommendationRes;

public interface ContractVehicleService {
    PassengerRes getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);

    void assignVehicleForContract (ContractVehicleReq contractVehicleReq);

    ContractVehicleRes getContractVehicles(int contractId);

    ContractVehicleStatus getVehicleStatus(int contractId, int issuedVehicleId);

    void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq);

    TripListRes getTrips(TripListReq tripListReq, int viewOption);

    void startTrip(TripReq tripReq);

    void endTrip(TripReq tripReq);

    VehicleRecommendationRes getRecommendations(VehicleRecommendationReq vehicleRecommendationReq);
}
