package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.mapper.ContractDetailMapper;
import com.fa20se28.vma.mapper.ContractDetailScheduleMapper;
import com.fa20se28.vma.mapper.ContractMapper;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ContractComponentImpl implements ContractComponent {
    private final ContractMapper contractMapper;
    private final ContractDetailMapper contractDetailMapper;
    private final ContractDetailScheduleMapper contractDetailScheduleMapper;

    public ContractComponentImpl(ContractMapper contractMapper, ContractDetailMapper contractDetailMapper, ContractDetailScheduleMapper contractDetailScheduleMapper) {
        this.contractMapper = contractMapper;
        this.contractDetailMapper = contractDetailMapper;
        this.contractDetailScheduleMapper = contractDetailScheduleMapper;
    }

    @Override
    @Transactional
    public void createContract(ContractReq contractReq) {
        int row = contractMapper.createContract(contractReq, ContractStatus.NOT_STARTED);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        } else {
            for (ContractTripReq trip: contractReq.getTrips()) {
                int contractTripRow = contractDetailMapper.createContractDetail(trip, contractMapper.getContractId(contractReq.getContractOwnerId()));

                if (contractTripRow == 0) {
                    throw new DataException("Unknown error occurred. Data not modified!");
                } else {
                    int contractDetailId = contractDetailMapper.getContractDetailId(contractMapper.getContractId(contractReq.getContractOwnerId()));

                    for (ContractTripScheduleReq location: trip.getLocations()) {
                        int scheduleRow = contractDetailScheduleMapper.createContractSchedule(
                                location.getLocation(),
                                contractDetailId);

                        if (scheduleRow == 0) {
                            throw new DataException("Unknown error occurred. Data not modified!");
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<ContractLM> getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum) {
        return contractMapper.getContracts(contractPageReq, viewOption, pageNum*15);
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
        int row = contractDetailScheduleMapper.updateContractSchedule(contractTripScheduleUpdateReq);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public ContractDetail getContractDetails(int contractId) {
        return contractMapper.getContractDetails(contractId);
    }
}
