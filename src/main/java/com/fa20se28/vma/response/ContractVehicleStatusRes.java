package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.ContractVehicleStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContractVehicleStatusRes {
    public List<ContractVehicleStatus> getContractVehicleStatus() {
        return Stream.of(ContractVehicleStatus.values()).collect(Collectors.toList());
    }
}
