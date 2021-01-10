package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.component.VehicleMiscComponent;
import com.fa20se28.vma.configuration.Combinations;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.NotificationType;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.response.VehicleContractRes;
import com.fa20se28.vma.service.ContractVehicleService;
import com.fa20se28.vma.service.FirebaseService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ContractVehicleServiceImpl implements ContractVehicleService {
    private final ContractVehicleComponent contractVehicleComponent;
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;
    private final VehicleComponent vehicleComponent;
    private final VehicleMiscComponent vehicleMiscComponent;

    public ContractVehicleServiceImpl(ContractVehicleComponent contractVehicleComponent, UserComponent userComponent, FirebaseService firebaseService, VehicleComponent vehicleComponent, VehicleMiscComponent vehicleMiscComponent) {
        this.contractVehicleComponent = contractVehicleComponent;
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
        this.vehicleComponent = vehicleComponent;
        this.vehicleMiscComponent = vehicleMiscComponent;
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

        ClientRegistrationToken clientRegistrationToken = userComponent.findClientRegistrationTokenByUserId(
                vehicleComponent.getCurrentDriver(contractVehicleReq.getVehicleId()).getUserId());

       if (clientRegistrationToken != null) {
           NotificationData notificationData = new NotificationData(
                   NotificationType.CONTRACT_ASSIGNED,
                   "You have been assigned with a trip!",
                   String.valueOf(contractVehicleReq.getContractTripId()),
                   null);

           firebaseService.notifyUserByFCMToken(clientRegistrationToken, notificationData);
       }
    }

    @Override
    public ContractVehicleRes getContractVehiclesByContractTripId(int contractDetailId) {
        return new ContractVehicleRes(contractVehicleComponent.getContractVehicles(contractDetailId));
    }

    @Override
    public ContractVehicleStatus getVehicleStatus(int contractTripId, int issuedVehicleId) {
        return contractVehicleComponent.getVehicleStatus(contractTripId, issuedVehicleId);
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
    public TripListRes getCurrentTrip(int issuedVehicleId) {
        return new TripListRes(contractVehicleComponent.getCurrentTrip(issuedVehicleId));
    }

    @Override
    public void startTrip(TripReq tripReq) {
        int contractBegin = contractVehicleComponent.startAndEndTrip(tripReq, false);

        if (contractBegin == 1) {
            NotificationData startContractData = new NotificationData(
                    NotificationType.CONTRACT_STARTED,
                    "Contract with ID " + tripReq.getContractId() + " is in progress",
                    String.valueOf(tripReq.getContractId())
                    , null);

            firebaseService.notifySubscribersByTopic("admin", startContractData);
        }

        NotificationData startTripData = new NotificationData(
                NotificationType.START_TRIP,
                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is on route",
                String.valueOf(tripReq.getVehicleId())
                , null);

        firebaseService.notifySubscribersByTopic("admin", startTripData);
    }

    @Override
    public void endTrip(TripReq tripReq) {
        int contractCompleted = contractVehicleComponent.startAndEndTrip(tripReq, true);

        NotificationData endTripData = new NotificationData(
                NotificationType.END_TRIP,
                "Vehicle with ID " + tripReq.getVehicleId() + " assigned to contract with ID " + tripReq.getContractId() + " is finished",
                String.valueOf(tripReq.getVehicleId())
                , null);

        firebaseService.notifySubscribersByTopic("admin", endTripData);

        if (contractCompleted == 1) {
            NotificationData endContractData = new NotificationData(
                    NotificationType.CONTRACT_COMPLETED,
                    "Contract with ID " + tripReq.getContractId() + " is completed",
                    String.valueOf(tripReq.getContractId()),
                    null);

            firebaseService.notifySubscribersByTopic("admin", endContractData);
        }
    }

    @Override
    public VehicleContractRes getAvailableVehicles(VehicleContractReq vehicleContractReq, int pageNum, int displayAll) {
        return new VehicleContractRes(null, contractVehicleComponent.getAvailableVehicles(vehicleContractReq, pageNum, displayAll));
    }

    @Override
    public int getTotalAvailableVehicles(VehicleContractReq vehicleContractReq, int displayAll) {
        return contractVehicleComponent.getTotalAvailableVehicles(vehicleContractReq, displayAll);
    }

    @Override
    public VehicleContractRes getAvailableVehiclesAuto(VehicleContractAutoReq vehicleContractAutoReq, int pageNum, int displayAll) {
        List<Integer> seats = vehicleMiscComponent.getSeatsList();
        Combinations combinations = new Combinations(seats, vehicleContractAutoReq.getPassengerCount(), vehicleContractAutoReq.getVehicleCount());
        combinations.calculateCombinations();
        Collections.sort(seats);

        vehicleContractAutoReq.getRequest().setSeatsMin(seats.get(0));
        vehicleContractAutoReq.getRequest().setSeatsMax(seats.get(seats.size()-1));

        if (combinations.getResult().size()!=0) {
            return new VehicleContractRes(combinations.getResult(), contractVehicleComponent.getAvailableVehicles(vehicleContractAutoReq.getRequest(), pageNum, displayAll));
        } else {
            return new VehicleContractRes(null, null);
        }
    }

    @Override
    public int getTotalAvailableVehiclesAuto(VehicleContractAutoReq vehicleContractAutoReq, int displayAll) {
        List<Integer> seats = vehicleMiscComponent.getSeatsList();
        vehicleContractAutoReq.getRequest().setSeatsMin(seats.get(0));
        vehicleContractAutoReq.getRequest().setSeatsMax(seats.get(seats.size()-1));

        return contractVehicleComponent.getTotalAvailableVehicles(vehicleContractAutoReq.getRequest(), displayAll);
    }


}
