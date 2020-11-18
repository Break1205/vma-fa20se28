package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.*;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ContractVehicleComponentImpl implements ContractVehicleComponent {
    private final PassengerMapper passengerMapper;
    private final ContractMapper contractMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleMapper vehicleMapper;

    public ContractVehicleComponentImpl(PassengerMapper passengerMapper, ContractMapper contractMapper, ContractVehicleMapper contractVehicleMapper, IssuedVehicleMapper issuedVehicleMapper, VehicleMapper vehicleMapper) {
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
        int currentIssuedId = issuedVehicleMapper.getCurrentIssuedVehicleId(contractVehicleReq.getVehicleId());
        ContractDetail contractDetail = contractMapper.getContractDetails(contractVehicleReq.getContractId());

        if (contractVehicleMapper.checkIfVehicleIsBusy(currentIssuedId, contractDetail.getDurationFrom(), contractDetail.getDurationTo())) {
            throw new DataException("Vehicle is still occupied!");
        } else {
            if (contractVehicleMapper.checkIfVehicleIsAlreadyAssignedToContract(currentIssuedId, contractVehicleReq.getContractId())) {
                throw new DataException("Vehicle is already assigned to the contract!");
            } else {
                int row = contractVehicleMapper.assignVehicleForContract(
                        contractVehicleReq.getContractId(),
                        currentIssuedId,
                        ContractVehicleStatus.NOT_STARTED);

                if (row == 0) {
                    throw new DataException("Unknown error occurred. Data not modified!");
                }
            }
        }

    }

    @Override
    public List<VehicleBasic> getContractVehicles(int contractId) {
        return contractVehicleMapper.getContractVehicles(contractId);
    }

    @Override
    public ContractVehicleStatus getVehicleStatus(int contractId, int issuedVehicleId) {
        return contractVehicleMapper.getVehicleStatus(contractId, issuedVehicleId);
    }

    @Override
    @Transactional
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        int row = contractVehicleMapper.updateContractedVehicleStatus(
                contractVehicleStatusUpdateReq.getContractId(),
                issuedVehicleMapper.getCurrentIssuedVehicleId(contractVehicleStatusUpdateReq.getVehicleId()),
                contractVehicleStatusUpdateReq.getVehicleStatus());

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void startAndEndTrip(TripReq tripReq, boolean option) {
        int contractVehicleRow;
        int vehicleRow;
        int currentIssuedId = issuedVehicleMapper.getCurrentIssuedVehicleId(tripReq.getVehicleId());

        ContractDetail detail = contractMapper.getContractDetails(tripReq.getContractId());

        if (!option) {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.AVAILABLE)) {
                throw new DataException("Vehicle is still occupied!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractId(), currentIssuedId, ContractVehicleStatus.IN_PROGRESS);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.ON_ROUTE);

                if (detail.getContractStatus().equals(ContractStatus.NOT_STARTED)) {
                    startContract(tripReq.getContractId());
                }
            }
        } else {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.ON_ROUTE)) {
                throw new DataException("Vehicle is not used!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractId(), currentIssuedId, ContractVehicleStatus.COMPLETED);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.AVAILABLE);
                if (detail.getContractStatus().equals(ContractStatus.IN_PROGRESS)) {
                    if (contractVehicleMapper.getCompletedVehicleCount(tripReq.getContractId()) >= detail.getEstimatedVehicleCount()) {
                        completeContract(tripReq.getContractId());
                    }
                }
            }
        }

        if (contractVehicleRow == 0 || vehicleRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }

    }

    @Override
    public List<Trip> getVehicleTrips(TripListReq tripListReq) {
        return contractVehicleMapper.getVehicleTrips(
                tripListReq.getIssuedVehicleId(),
                tripListReq.getDepartureTime(),
                tripListReq.getDestinationTime(),
                tripListReq.getVehicleStatus());
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
}
