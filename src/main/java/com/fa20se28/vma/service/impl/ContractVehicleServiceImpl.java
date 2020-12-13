package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.enums.ContractVehicleStatus;
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
    public ContractVehicleRes getContractVehicles(int contractId) {
        return new ContractVehicleRes(contractVehicleComponent.getContractVehicles(contractId));
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
        contractVehicleComponent.startAndEndTrip(tripReq, false);

        // Notification for start trip + contract. Disabled for now.

//        NotificationData startContractData = null;

//        if (contractVehicleComponent.startAndEndTrip(tripReq, false) == 1) {
//            startContractData = new NotificationData(
//                    NotificationType.CONTRACT_STARTED,
//                    "Contract with ID " + tripReq.getContractId() + " is in progress",
//                    String.valueOf(tripReq.getContractId()));
//        }
//
//        NotificationData startTripData = new NotificationData(
//                NotificationType.START_TRIP,
//                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is on route",
//                String.valueOf(tripReq.getVehicleId()));


//        for  (ClientRegistrationToken adminToken: userComponent.getAdminRegistrationTokens()) {
//            if (adminToken != null) {
//                firebaseService.notifyUserByFCMToken(
//                        adminToken,
//                        startTripData);
//
//                if (startContractData != null) {
//                    firebaseService.notifyUserByFCMToken(
//                            adminToken,
//                            startContractData);
//                }
//            }
//        }
    }

    @Override
    public void endTrip(TripReq tripReq) {
        contractVehicleComponent.startAndEndTrip(tripReq, true);

        // Notification for end trip + contract. Disabled for now.

//        NotificationData endContractData = null;
//
//        if (contractVehicleComponent.startAndEndTrip(tripReq, true) == 1) {
//            endContractData = new NotificationData(
//                    NotificationType.CONTRACT_COMPLETED,
//                    "Contract with ID " + tripReq.getContractId() + " is completed",
//                    String.valueOf(tripReq.getContractId()));
//        }
//
//        NotificationData endTripData = new NotificationData(
//                NotificationType.END_TRIP,
//                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is finished",
//                String.valueOf(tripReq.getVehicleId()));
//
//
//        for  (ClientRegistrationToken adminToken: userComponent.getAdminRegistrationTokens()) {
//            if (adminToken != null) {
//                firebaseService.notifyUserByFCMToken(
//                        adminToken,
//                        endTripData);
//
//                if (endContractData != null) {
//                    firebaseService.notifyUserByFCMToken(
//                            adminToken,
//                            endContractData);
//                }
//            }
//        }
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
