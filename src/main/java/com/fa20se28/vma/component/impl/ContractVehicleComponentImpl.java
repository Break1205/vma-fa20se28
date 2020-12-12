package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.*;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ContractVehicleComponentImpl implements ContractVehicleComponent {
    private final PassengerMapper passengerMapper;
    private final ContractMapper contractMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleMapper vehicleMapper;

    public ContractVehicleComponentImpl(PassengerMapper passengerMapper,
                                        ContractMapper contractMapper,
                                        ContractVehicleMapper contractVehicleMapper,
                                        IssuedVehicleMapper issuedVehicleMapper,
                                        VehicleMapper vehicleMapper) {
        this.passengerMapper = passengerMapper;
        this.contractMapper = contractMapper;
        this.contractVehicleMapper = contractVehicleMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public List<Passenger> getPassengerList(int contractVehicleId) {
        return passengerMapper.getPassengersFromContractId(contractVehicleId);
    }

    @Override
    @Transactional
    public void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq) {
        for (PassengerReq passengerReq : contractVehiclePassengerReq.getPassengerList()) {
            int row = passengerMapper.createPassenger(passengerReq, contractVehiclePassengerReq.getContractVehicleId());

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            }
        }
    }

    @Override
    @Transactional
    public void assignVehicleForContract(ContractVehicleReq contractVehicleReq) {
        Optional<IssuedVehicle> optionalIssuedVehicle =
                issuedVehicleMapper.getCurrentIssuedVehicleWithDriverById(contractVehicleReq.getVehicleId());

        if (optionalIssuedVehicle.isPresent()) {
            if (optionalIssuedVehicle.get().getDriverId() == null) {
                throw new InvalidParamException("Vehicle with id: " + contractVehicleReq.getVehicleId() + " doesn't have any driver");
            }
        } else {
            throw new ResourceNotFoundException("Vehicle with id: " + contractVehicleReq.getVehicleId() + " not found");
        }

        int row = contractVehicleMapper.assignVehicleForContract(
                contractVehicleReq.getContractDetailId(),
                optionalIssuedVehicle.get().getIssuedVehicleId(),
                ContractVehicleStatus.NOT_STARTED);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }

    }

    @Override
    public List<VehicleBasic> getContractVehicles(int contractId) {
        return contractVehicleMapper.getContractVehicles(contractId);
    }

    @Override
    public ContractVehicleStatus getVehicleStatus(int contractDetailId, int issuedVehicleId) {
        return contractVehicleMapper.getVehicleStatus(contractDetailId, issuedVehicleId);
    }

    @Override
    @Transactional
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        int row = contractVehicleMapper.updateContractedVehicleStatus(
                contractVehicleStatusUpdateReq.getContractDetailId(),
                issuedVehicleMapper.getCurrentIssuedVehicleId(contractVehicleStatusUpdateReq.getVehicleId()),
                contractVehicleStatusUpdateReq.getVehicleStatus());

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public int startAndEndTrip(TripReq tripReq, boolean option) {
        int contractVehicleRow;
        int vehicleRow;

        VehicleDetail vehicleDetail = vehicleMapper.getVehicleDetails(tripReq.getVehicleId());
        if (vehicleDetail == null) {
            throw new ResourceNotFoundException("Vehicle with id: " + tripReq.getVehicleId() + " not found");
        }
        int currentIssuedId = issuedVehicleMapper.getCurrentIssuedVehicleId(tripReq.getVehicleId());

        ContractDetail detail = contractMapper.getContractDetails(tripReq.getContractId());

        if (!option) {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.AVAILABLE)) {
                throw new DataException("Vehicle is still occupied!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractDetailId(), currentIssuedId, ContractVehicleStatus.IN_PROGRESS);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.ON_ROUTE);

                if (detail.getContractStatus().equals(ContractStatus.NOT_STARTED)) {
                    startContract(tripReq.getContractId());
                    return 1;
                }
            }
        } else {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.ON_ROUTE)) {
                throw new DataException("Vehicle is not used!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractDetailId(), currentIssuedId, ContractVehicleStatus.COMPLETED);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.AVAILABLE);
                if (detail.getContractStatus().equals(ContractStatus.IN_PROGRESS)) {
                    List<ContractVehicle> contractVehicles = contractVehicleMapper.getContractVehiclesByContractId(tripReq.getContractId());
                    boolean contractFinished = true;
                    for (ContractVehicle contractVehicle : contractVehicles) {
                        if (!contractVehicle.getContractVehicleStatus().equals(ContractVehicleStatus.COMPLETED)) {
                            contractFinished = false;
                            break;
                        }
                    }
                    if (contractFinished) {
                        completeContract(tripReq.getContractId());
                        return 1;
                    }
                }
            }
        }

        if (contractVehicleRow == 0 || vehicleRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }

        return 0;
    }

    @Override
    public List<Trip> getVehicleTrips(TripListReq tripListReq, int page) {
        return contractVehicleMapper.getVehicleTrips(
                tripListReq.getIssuedVehicleId(),
                tripListReq.getVehicleStatus(),
                page * 15);
    }

    @Override
    @Transactional
    public void startContract(int contractId) {
        int row = contractMapper.updateStatus(ContractStatus.IN_PROGRESS, contractId);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void completeContract(int contractId) {
        int contractStatusRow = contractMapper.updateStatus(ContractStatus.FINISHED, contractId);

        int completedVehicleCount = contractVehicleMapper.getCompletedVehicleCount(contractId);
        int actualPassengerCount = passengerMapper.getPassengerCountFromContract(contractId);

        int actualNumberRow = contractMapper.updateContractActual(actualPassengerCount, completedVehicleCount, contractId);
        if (contractStatusRow == 0 || actualNumberRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public List<VehicleRecommendation> getRecommendations(VehicleRecommendationReq vehicleRecommendationReq, int viewOption) {
        return contractVehicleMapper.getRecommendations(vehicleRecommendationReq, viewOption * 10);
    }

    @Override
    public int getTotalRecommendations(VehicleRecommendationReq vehicleRecommendationReq) {
        return contractVehicleMapper.getRecommendationCount(vehicleRecommendationReq);
    }
}
