package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Passenger;
import com.fa20se28.vma.model.VehicleBasic;
import com.fa20se28.vma.request.ContractVehiclePassengerReq;
import com.fa20se28.vma.request.ContractVehicleReq;
import com.fa20se28.vma.request.ContractVehicleStatusUpdateReq;
import com.fa20se28.vma.request.TripReq;

import java.util.List;

public interface ContractVehicleComponent {
    List<Passenger> getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);

    void assignVehicleForContract(ContractVehicleReq contractVehicleReq);

    List<VehicleBasic> getContractVehicles(int contractId);

    void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq);

    void startAndEndTrip(TripReq tripReq);
}
