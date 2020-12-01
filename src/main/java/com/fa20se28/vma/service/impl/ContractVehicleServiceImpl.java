package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.NotificationType;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.response.VehicleRecommendationRes;
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
    public ContractVehicleRes getContractVehicles(int contractId) {
        return new ContractVehicleRes(contractVehicleComponent.getContractVehicles(contractId));
    }

    @Override
    public ContractVehicleStatus getVehicleStatus(int contractId, int issuedVehicleId) {
        return contractVehicleComponent.getVehicleStatus(contractId, issuedVehicleId);
    }

    @Override
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        contractVehicleComponent.updateContractVehicleStatus(contractVehicleStatusUpdateReq);
    }


    @Override
    public TripListRes getTrips(TripListReq tripListReq, int viewOption) {
        return new TripListRes(contractVehicleComponent.getVehicleTrips(tripListReq, viewOption));
    }

    @Override
    public void startTrip(TripReq tripReq) {
        NotificationData startContractData = null;

        if (contractVehicleComponent.startAndEndTrip(tripReq, false) == 1) {
            startContractData = new NotificationData(
                    NotificationType.CONTRACT_STARTED,
                    "Contract with ID " + tripReq.getContractId() + " is in progress",
                    String.valueOf(tripReq.getContractId()));
        }

        NotificationData startTripData = new NotificationData(
                NotificationType.START_TRIP,
                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is on route",
                String.valueOf(tripReq.getVehicleId()));


        for  (ClientRegistrationToken adminToken: userComponent.getAdminRegistrationTokens()) {
            if (adminToken != null) {
                firebaseService.notifyUserByFCMToken(
                        adminToken,
                        startTripData);

                if (startContractData != null) {
                    firebaseService.notifyUserByFCMToken(
                            adminToken,
                            startContractData);
                }
            }
        }
    }

    @Override
    public void endTrip(TripReq tripReq) {
        NotificationData endContractData = null;

        if (contractVehicleComponent.startAndEndTrip(tripReq, true) == 1) {
            endContractData = new NotificationData(
                    NotificationType.CONTRACT_COMPLETED,
                    "Contract with ID " + tripReq.getContractId() + " is completed",
                    String.valueOf(tripReq.getContractId()));
        }

        NotificationData endTripData = new NotificationData(
                NotificationType.END_TRIP,
                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is finished",
                String.valueOf(tripReq.getVehicleId()));


        for  (ClientRegistrationToken adminToken: userComponent.getAdminRegistrationTokens()) {
            if (adminToken != null) {
                firebaseService.notifyUserByFCMToken(
                        adminToken,
                        endTripData);

                if (endContractData != null) {
                    firebaseService.notifyUserByFCMToken(
                            adminToken,
                            endContractData);
                }
            }
        }
    }

    @Override
    public VehicleRecommendationRes getRecommendations(VehicleRecommendationReq vehicleRecommendationReq, int viewOption) {
        return new VehicleRecommendationRes(contractVehicleComponent.getRecommendations(vehicleRecommendationReq, viewOption));
    }

    @Override
    public int getTotalRecommendations(VehicleRecommendationReq vehicleRecommendationReq) {
        return contractVehicleComponent.getTotalRecommendations(vehicleRecommendationReq);
    }
}
