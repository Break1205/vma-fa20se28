package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.NotificationType;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.response.VehicleContractRes;
import com.fa20se28.vma.service.ContractVehicleService;
import com.fa20se28.vma.service.FirebaseService;
import org.springframework.stereotype.Service;

@Service
public class ContractVehicleServiceImpl implements ContractVehicleService {
    private final ContractVehicleComponent contractVehicleComponent;
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;

    public ContractVehicleServiceImpl(ContractVehicleComponent contractVehicleComponent, UserComponent userComponent, FirebaseService firebaseService) {
        this.contractVehicleComponent = contractVehicleComponent;
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
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
    public ContractVehicleRes getContractVehiclesByContractDetailId(int contractDetailId) {
        return new ContractVehicleRes(contractVehicleComponent.getContractVehicles(contractDetailId));
    }

    @Override
    public ContractVehicleStatus getVehicleStatus(int contractDetailId, int issuedVehicleId) {
        return contractVehicleComponent.getVehicleStatus(contractDetailId, issuedVehicleId);
    }

    @Override
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        contractVehicleComponent.updateContractVehicleStatus(contractVehicleStatusUpdateReq);
    }


    @Override
    public TripListRes getTrips(TripListReq tripListReq, int page) {
        return new TripListRes(contractVehicleComponent.getVehicleTrips(tripListReq, page));
    }

    @Override
    public void startTrip(TripReq tripReq) {
        int contractBegin = contractVehicleComponent.startAndEndTrip(tripReq, false);

        if (contractBegin == 1) {
            NotificationData startContractData = new NotificationData(
                    NotificationType.CONTRACT_STARTED,
                    "Contract with ID " + tripReq.getContractId() + " is in progress",
                    String.valueOf(tripReq.getContractId()));

            firebaseService.notifySubscribersByTopic("admin", startContractData);
        }

        NotificationData startTripData = new NotificationData(
                NotificationType.START_TRIP,
                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is on route",
                String.valueOf(tripReq.getVehicleId()));

        firebaseService.notifySubscribersByTopic("admin", startTripData);
    }

    @Override
    public void endTrip(TripReq tripReq) {
        int contractCompleted = contractVehicleComponent.startAndEndTrip(tripReq, true);

        NotificationData endTripData = new NotificationData(
                NotificationType.END_TRIP,
                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is finished",
                String.valueOf(tripReq.getVehicleId()));

        firebaseService.notifySubscribersByTopic("admin", endTripData);

        if (contractCompleted == 1) {
            NotificationData endContractData = new NotificationData(
                    NotificationType.CONTRACT_COMPLETED,
                    "Contract with ID " + tripReq.getContractId() + " is completed",
                    String.valueOf(tripReq.getContractId()));

            firebaseService.notifySubscribersByTopic("admin", endContractData);
        }
    }

    @Override
    public VehicleContractRes getAvailableVehicles(VehicleContractReq vehicleContractReq, int pageNum, int viewOption) {
        return new VehicleContractRes(contractVehicleComponent.getAvailableVehicles(vehicleContractReq, pageNum, viewOption));
    }

    @Override
    public int getTotalAvailableVehicles(VehicleContractReq vehicleContractReq, int viewOption) {
        return contractVehicleComponent.getTotalAvailableVehicles(vehicleContractReq, viewOption);
    }
}
