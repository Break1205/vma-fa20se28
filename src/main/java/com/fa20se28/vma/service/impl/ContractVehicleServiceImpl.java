package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.service.ContractVehicleService;
import org.springframework.stereotype.Service;

@Service
public class ContractVehicleServiceImpl implements ContractVehicleService {
    private final ContractVehicleComponent contractVehicleComponent;

    public ContractVehicleServiceImpl(ContractVehicleComponent contractVehicleComponent) {
        this.contractVehicleComponent = contractVehicleComponent;
    }

    @Override
    public PassengerRes getPassengerList(int contractVehicleId) {
        return new PassengerRes(contractVehicleComponent.getPassengerList(contractVehicleId));
    }

    @Override
    public void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq) {
        contractVehicleComponent.createPassengerList(contractVehiclePassengerReq);
    }

    @Override
    public void assignVehicleForContract(ContractVehicleReq contractVehicleReq) {
        contractVehicleComponent.assignVehicleForContract(contractVehicleReq);
    }

    @Override
    public ContractVehicleRes getContractVehicles(int contractId) {
        return new ContractVehicleRes(contractVehicleComponent.getContractVehicles(contractId));
    }

    @Override
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        contractVehicleComponent.updateContractVehicleStatus(contractVehicleStatusUpdateReq);
    }

    @Override
    public void startAndEndTrip(TripReq tripReq) {
        contractVehicleComponent.startAndEndTrip(tripReq);
    }

    @Override
    public TripListRes getTrips(TripListReq tripListReq) {
        return new TripListRes(contractVehicleComponent.getVehicleTrips(tripListReq));
    }
}
