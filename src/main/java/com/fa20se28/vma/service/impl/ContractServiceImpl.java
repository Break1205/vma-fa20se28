package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.enums.NotificationType;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractTrip;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractTripReq;
import com.fa20se28.vma.response.ContractDetailRes;
import com.fa20se28.vma.response.ContractPageRes;
import com.fa20se28.vma.service.ContractService;
import com.fa20se28.vma.service.FirebaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractComponent contractComponent;
    private final FirebaseService firebaseService;
    private final UserComponent userComponent;
    private final VehicleComponent vehicleComponent;

    public ContractServiceImpl(ContractComponent contractComponent, FirebaseService firebaseService, UserComponent userComponent, VehicleComponent vehicleComponent) {
        this.contractComponent = contractComponent;
        this.firebaseService = firebaseService;
        this.userComponent = userComponent;
        this.vehicleComponent = vehicleComponent;
    }

    private void createNotificationForUser(List<ContractTripReq> contractTripReqs, List<ContractTrip> contractTrips, boolean newTrip) {
        if (newTrip) {
            for (ContractTripReq trip : contractTripReqs) {
                for (String vehicleId : trip.getAssignedVehicles()) {
                    ClientRegistrationToken clientRegistrationToken = userComponent.findClientRegistrationTokenByUserId(
                            vehicleComponent.getCurrentDriver(vehicleId).getUserId());

                    if (clientRegistrationToken != null) {
                        NotificationData notificationData = new NotificationData(
                                NotificationType.CONTRACT_ASSIGNED,
                                "You have been assigned with a trip!",
                                String.valueOf(trip.getContractTripId())
                                , null);

                        firebaseService.notifyUserByFCMToken(clientRegistrationToken, notificationData);
                    }
                }
            }
        } else {
            for (ContractTrip trip : contractTrips) {
                for (String vehicleId : trip.getAssignedVehicles()) {
                    ClientRegistrationToken clientRegistrationToken = userComponent.findClientRegistrationTokenByUserId(
                            vehicleComponent.getCurrentDriver(vehicleId).getUserId());

                    if (clientRegistrationToken != null) {
                        NotificationData notificationData = new NotificationData(
                                NotificationType.CONTRACT_DROPPED,
                                "Your trip has been dropped!",
                                String.valueOf(trip.getContractTripId())
                                , null);

                        firebaseService.notifyUserByFCMToken(clientRegistrationToken, notificationData);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void createContract(ContractReq contractReq) {
        contractComponent.createContract(contractReq);
        createNotificationForUser(contractReq.getTrips(), null, true);
    }

    @Override
    public ContractPageRes getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum) {
        return new ContractPageRes(contractComponent.getContracts(contractPageReq, viewOption, pageNum));
    }

    @Override
    public int getTotalContracts(ContractPageReq contractPageReq, int viewOption) {
        return contractComponent.getTotalContracts(contractPageReq, viewOption);
    }

    @Override
    public void updateContractStatus(ContractStatus contractStatus, int contractId) {
        contractComponent.updateContractStatus(contractStatus, contractId);
    }

    @Override
    public void updateContract(ContractReq contractReq) {
        ContractDetail detail = contractComponent.getContractDetails(contractReq.getContractId());
        createNotificationForUser(null, detail.getTrips(), false);

        contractComponent.updateContract(contractReq);
        createNotificationForUser(contractReq.getTrips(), null, true);
    }

    @Override
    public ContractDetailRes getContractDetails(int contractId) {
        return new ContractDetailRes(contractComponent.getContractDetails(contractId));
    }
}
