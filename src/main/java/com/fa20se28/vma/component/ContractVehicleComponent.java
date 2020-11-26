package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.model.Passenger;
import com.fa20se28.vma.model.Trip;
import com.fa20se28.vma.model.VehicleBasic;
import com.fa20se28.vma.model.VehicleRecommendation;
import com.fa20se28.vma.request.*;

import java.util.List;

public interface ContractVehicleComponent {
    List<Passenger> getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);

    void assignVehicleForContract(ContractVehicleReq contractVehicleReq);

    List<VehicleBasic> getContractVehicles(int contractId);

    ContractVehicleStatus getVehicleStatus(int contractId, int issuedVehicleId);

    void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq);

    int startAndEndTrip(TripReq tripReq, boolean option);

    List<Trip> getVehicleTrips(TripListReq tripListReq, int viewOption);

    void startContract(int contractId);

    void completeContract(int contractId);

    List<VehicleRecommendation> getRecommendations(VehicleRecommendationReq vehicleRecommendationReq, int viewOption);

    int getTotalRecommendations(VehicleRecommendationReq vehicleRecommendationReq);
}
