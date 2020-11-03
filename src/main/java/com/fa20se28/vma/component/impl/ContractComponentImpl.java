package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.mapper.ContractMapper;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractUpdateReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ContractComponentImpl implements ContractComponent {
    private final ContractMapper contractMapper;

    public ContractComponentImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    @Override
    @Transactional
    public void createContract(ContractReq contractReq) {
        int row = contractMapper.createContract(contractReq, ContractStatus.UNFINISHED);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public List<ContractLM> getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum) {
        return contractMapper.getContracts(contractPageReq, viewOption, pageNum*15);
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


}
