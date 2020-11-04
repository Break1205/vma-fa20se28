package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Passenger;
import com.fa20se28.vma.request.ContractVehiclePassengerReq;

import java.util.List;

public interface ContractVehicleComponent {
    List<Passenger> getPassengerList(int contractVehicleId);

    void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq);
}
