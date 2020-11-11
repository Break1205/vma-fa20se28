package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.ContractStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContractStatusRes {
    public List<ContractStatus> getContractStatus() {
        return Stream.of(ContractStatus.values()).collect(Collectors.toList());
    }
}
