package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.mapper.ContractDetailMapper;
import com.fa20se28.vma.mapper.ContractDetailScheduleMapper;
import com.fa20se28.vma.mapper.ContractMapper;
import com.fa20se28.vma.mapper.ContractVehicleMapper;
import com.fa20se28.vma.mapper.IssuedVehicleMapper;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractTripReq;
import com.fa20se28.vma.request.ContractTripScheduleReq;
import com.fa20se28.vma.request.ContractTripScheduleUpdateReq;
import com.fa20se28.vma.request.ContractTripUpdateReq;
import com.fa20se28.vma.request.ContractUpdateReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ContractComponentImpl implements ContractComponent {
    private final ContractMapper contractMapper;
    private final ContractDetailMapper contractDetailMapper;
    private final ContractDetailScheduleMapper contractDetailScheduleMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;

    public ContractComponentImpl(ContractMapper contractMapper,
                                 ContractDetailMapper contractDetailMapper,
                                 ContractDetailScheduleMapper contractDetailScheduleMapper,
                                 ContractVehicleMapper contractVehicleMapper, IssuedVehicleMapper issuedVehicleMapper) {
        this.contractMapper = contractMapper;
        this.contractDetailMapper = contractDetailMapper;
        this.contractDetailScheduleMapper = contractDetailScheduleMapper;
        this.contractVehicleMapper = contractVehicleMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
    }


    // todo: contract_vehicle
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
            for (ContractTripReq trip : contractReq.getTrips()) {
                int contractTripRow = contractDetailMapper.createContractDetail(trip, contractMapper.getContractId(contractReq.getContractOwnerId()));
                if (contractTripRow == 0) {
                    throw new DataException("Can not insert Contract Detail record");
                } else {
                    for (String vehicleId : trip.getAssignedVehicles()) {
                        int currentIssuedId = issuedVehicleMapper.getCurrentIssuedVehicleId(vehicleId);

                        int contractVehicleRow = contractVehicleMapper.assignVehicleForContract(
                                trip.getContractDetailId(),
                                currentIssuedId,
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
    public void updateContract(ContractUpdateReq contractUpdateReq) {
        int row = contractMapper.updateContract(contractUpdateReq);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateContractTrip(ContractTripUpdateReq contractTripUpdateReq) {
        int row = contractDetailMapper.updateContractDetail(contractTripUpdateReq);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateContractTripSchedule(ContractTripScheduleUpdateReq contractTripScheduleUpdateReq) {
        int clearScheduleRow = contractDetailScheduleMapper.deleteContractSchedule(contractTripScheduleUpdateReq.getContractTripId());

        if (clearScheduleRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        } else {
            for (ContractTripScheduleReq location : contractTripScheduleUpdateReq.getLocations()) {
                int scheduleRow = contractDetailScheduleMapper.createContractSchedule(
                        location.getLocation(),
                        contractTripScheduleUpdateReq.getContractTripId());

                if (scheduleRow == 0) {
                    throw new DataException("Unknown error occurred. Data not modified!");
                }
            }
        }
    }

    @Override
    public ContractDetail getContractDetails(int contractId) {
        return contractMapper.getContractDetails(contractId);
    }
}
