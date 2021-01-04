package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.model.Passenger;
import com.fa20se28.vma.model.Trip;
import com.fa20se28.vma.model.VehicleBasic;
import com.fa20se28.vma.model.VehicleContract;
import com.fa20se28.vma.request.*;

import java.util.List;

public interface ContractVehicleComponent {
    List<Passenger> getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);

    void assignVehicleForContract(ContractVehicleReq contractVehicleReq);

    List<VehicleBasic> getContractVehicles(int contractId);

    ContractVehicleStatus getVehicleStatus(int contractTripId, int issuedVehicleId);

    void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq);

    int startAndEndTrip(TripReq tripReq, boolean option);

    List<Trip> getVehicleTrips(TripListReq tripListReq, int page);

    List<Trip> getCurrentTrip(int issuedVehicleId);

    void startContract(int contractId);

    void completeContract(int contractId);

    List<VehicleContract> getAvailableVehicles(VehicleContractReq vehicleContractReq, int pageNum, int viewOption);

    int getTotalAvailableVehicles(VehicleContractReq vehicleContractReq, int viewOption);

    void assignBackUpVehicleForContract(String oldVehicleId, int contractTripId, BackUpVehicleReq backUpVehicleReq);
}
