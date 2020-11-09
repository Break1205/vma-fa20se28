package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.ContractVehicleMapper;
import com.fa20se28.vma.mapper.IssuedVehicleMapper;
import com.fa20se28.vma.mapper.PassengerMapper;
import com.fa20se28.vma.mapper.VehicleMapper;
import com.fa20se28.vma.model.Passenger;
import com.fa20se28.vma.model.VehicleBasic;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ContractVehicleComponentImpl implements ContractVehicleComponent {
    private final PassengerMapper passengerMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleMapper vehicleMapper;

    public ContractVehicleComponentImpl(PassengerMapper passengerMapper, ContractVehicleMapper contractVehicleMapper, IssuedVehicleMapper issuedVehicleMapper, VehicleMapper vehicleMapper) {
        this.passengerMapper = passengerMapper;
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
        String vehicleId = contractVehicleReq.getVehicleId();

        if (!vehicleMapper.getVehicleStatus(vehicleId).equals(VehicleStatus.AVAILABLE)) {
            throw new DataException("Vehicle is still occupied!");
        } else {
            int row = contractVehicleMapper.assignVehicleForContract(
                    contractVehicleReq.getContractId(),
                    issuedVehicleMapper.getCurrentIssuedVehicleId(vehicleId),
                    ContractVehicleStatus.NOT_STARTED);

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            }
        }

    }

    @Override
    public List<VehicleBasic> getContractVehicles(int contractId) {
        return contractVehicleMapper.getContractVehicles(contractId);
    }

    @Override
    @Transactional
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        int row = contractVehicleMapper.updateContractedVehicleStatus(
                contractVehicleStatusUpdateReq.getContractVehicleId(),
                contractVehicleStatusUpdateReq.getVehicleStatus());

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void startAndEndTrip(TripReq tripReq) {
        int contractVehicleRow;
        int vehicleRow;

        if (!tripReq.isOption()) {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.AVAILABLE)) {
                throw new DataException("Vehicle is still occupied!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractVehicleId(), ContractVehicleStatus.IN_PROGRESS);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.ON_ROUTE);
            }
        } else {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.ON_ROUTE)) {
                throw new DataException("Vehicle is not used!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractVehicleId(), ContractVehicleStatus.COMPLETED);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.AVAILABLE);
            }
        }

        if (contractVehicleRow == 0 && vehicleRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }

    }


}
