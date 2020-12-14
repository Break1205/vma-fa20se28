package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.mapper.ContractDetailMapper;
import com.fa20se28.vma.mapper.ContractDetailScheduleMapper;
import com.fa20se28.vma.mapper.ContractMapper;
import com.fa20se28.vma.mapper.ContractVehicleMapper;
import com.fa20se28.vma.mapper.IssuedVehicleMapper;
import com.fa20se28.vma.mapper.PassengerMapper;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.model.ContractVehicle;
import com.fa20se28.vma.model.IssuedVehicle;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractTripReq;
import com.fa20se28.vma.request.ContractTripScheduleReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ContractComponentImpl implements ContractComponent {
    private final ContractMapper contractMapper;
    private final ContractDetailMapper contractDetailMapper;
    private final ContractDetailScheduleMapper contractDetailScheduleMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final PassengerMapper passengerMapper;

    public ContractComponentImpl(ContractMapper contractMapper,
                                 ContractDetailMapper contractDetailMapper,
                                 ContractDetailScheduleMapper contractDetailScheduleMapper,
                                 ContractVehicleMapper contractVehicleMapper,
                                 IssuedVehicleMapper issuedVehicleMapper,
                                 PassengerMapper passengerMapper) {
        this.contractMapper = contractMapper;
        this.contractDetailMapper = contractDetailMapper;
        this.contractDetailScheduleMapper = contractDetailScheduleMapper;
        this.contractVehicleMapper = contractVehicleMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
        this.passengerMapper = passengerMapper;
    }


    @Override
    @Transactional
    public void createContract(ContractReq contractReq) {
        if (contractReq.isRoundTrip()) {
            if (contractReq.getTrips().size() != 2) {
                throw new InvalidParamException("This contract is round-trip. Therefore it needs 2 trips/vehicles");
            }
        } else {
            if (contractReq.getTrips().size() != 1) {
                throw new InvalidParamException("This contract is one-way-trip. Therefore it needs 1 trip/vehicle");
            }
        }
        int row = contractMapper.createContract(contractReq, ContractStatus.NOT_STARTED);

        if (row == 0) {
            throw new DataException("Can not insert Contract record!");
        } else {
            createContractDetailAndScheduleAndVehicles(contractReq);
        }
    }

    @Override
    public List<ContractLM> getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum) {
        return contractMapper.getContracts(contractPageReq, viewOption, pageNum * 15);
    }

    @Override
    public int getTotalContracts(ContractPageReq contractPageReq, int viewOption) {
        return contractMapper.getContractCount(contractPageReq, viewOption);
    }

    @Override
    @Transactional
    public void updateContractStatus(ContractStatus contractStatus, int contractId) {
        int row = contractMapper.updateStatus(contractStatus, contractId);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateContract(ContractReq contractReq) {
        if (contractReq.isRoundTrip()) {
            if (contractReq.getTrips().size() != 2) {
                throw new InvalidParamException("This contract is round-trip. Therefore it needs 2 trips/vehicles");
            }
        } else {
            if (contractReq.getTrips().size() != 1) {
                throw new InvalidParamException("This contract is one-way-trip. Therefore it needs 1 trip/vehicle");
            }
        }
        List<ContractVehicle> contractVehicles = contractVehicleMapper.getContractVehiclesByContractId(contractReq.getContractId());
        for (ContractVehicle contractVehicle : contractVehicles) {
            if (!contractVehicle.getContractVehicleStatus().equals(ContractVehicleStatus.NOT_STARTED)) {
                throw new ResourceIsInUsedException(
                        "Contract Vehicle with id: "
                                + contractVehicle.getContractVehicleId() + " is "
                                + contractVehicle.getContractVehicleStatus() + ". Can not modify");
            }
        }

        int deletedPassengersRecord = passengerMapper.deletePassengersFromContractVehicle(contractReq.getContractId());
        if (deletedPassengersRecord < 0) {
            throw new DataException("Can not delete passengers of contract vehicle id: " + contractReq.getContractId());
        }

        int deleteContractDetailSchedule = contractDetailScheduleMapper.deleteContractSchedule(contractReq.getContractId());
        if (deleteContractDetailSchedule < 0) {
            throw new DataException("Can not delete schedule of contract id: " + contractReq.getContractId());
        }

        int deletedContractVehicleRecords = contractVehicleMapper.deleteContractVehicles(contractReq.getContractId());
        if (deletedContractVehicleRecords == 0) {
            throw new DataException("Can not delete contract vehicles of contract id: " + contractReq.getContractId());
        }

        int deleteContractDetailRecords = contractDetailMapper.deleteContractDetailById(contractReq.getContractId());
        if (deleteContractDetailRecords == 0) {
            throw new DataException("Can not delete contract details of contract id: " + contractReq.getContractId());
        }

        int contractRecord = contractMapper.updateContract(contractReq);

        if (contractRecord == 0) {
            throw new DataException("Can not update contract record");
        } else {
            createContractDetailAndScheduleAndVehicles(contractReq);
        }
    }


    private void createContractDetailAndScheduleAndVehicles(ContractReq contractReq) {
        for (ContractTripReq trip : contractReq.getTrips()) {
            int contractTripRow = contractDetailMapper.createContractDetail(trip, contractMapper.getContractId(contractReq.getContractOwnerId()));
            if (contractTripRow == 0) {
                throw new DataException("Can not insert Contract Detail record");
            } else {
                Map<String, String> nonDuplicateAssignedVehicles = new HashMap<>();
                for (String vehicleId : trip.getAssignedVehicles()) {
                    if (nonDuplicateAssignedVehicles.containsKey(vehicleId)) {
                        throw new InvalidParamException("Duplicate vehicle id: " + vehicleId + " .A vehicle can only be on 1 trip at a time");
                    } else {
                        nonDuplicateAssignedVehicles.put(vehicleId, vehicleId);
                    }
                    Optional<IssuedVehicle> optionalIssuedVehicle =
                            issuedVehicleMapper.getCurrentIssuedVehicleWithDriverById(vehicleId);

                    if (optionalIssuedVehicle.isPresent()) {
                        if (optionalIssuedVehicle.get().getDriverId() == null) {
                            throw new InvalidParamException("Vehicle with id: " + vehicleId + " doesn't have any driver");
                        }
                    } else {
                        throw new ResourceNotFoundException("Vehicle with id: " + vehicleId + " not found");
                    }

                    int contractVehicleRow = contractVehicleMapper.assignVehicleForContract(
                            trip.getContractTripId(),
                            optionalIssuedVehicle.get().getIssuedVehicleId(),
                            ContractVehicleStatus.NOT_STARTED
                    );

                    if (contractVehicleRow == 0) {
                        throw new DataException("Can not insert Contract Vehicle record");
                    }
                }

                int contractDetailId = contractDetailMapper.getContractDetailId(contractMapper.getContractId(contractReq.getContractOwnerId()));

                for (ContractTripScheduleReq location : trip.getLocations()) {
                    int scheduleRow = contractDetailScheduleMapper.createContractSchedule(
                            location.getLocation(),
                            contractDetailId);

                    if (scheduleRow == 0) {
                        throw new DataException("Can not insert Contract Detail Schedule record");
                    }
                }
            }
        }
    }

    @Override
    public ContractDetail getContractDetails(int contractId) {
        return contractMapper.getContractDetails(contractId);
    }
}
