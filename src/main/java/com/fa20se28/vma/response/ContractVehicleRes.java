package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleBasic;

import java.util.List;

public class ContractVehicleRes {
    private List<VehicleBasic> contractVehicleList;

    public ContractVehicleRes(List<VehicleBasic> contractVehicleList) {
        this.contractVehicleList = contractVehicleList;
    }

    public List<VehicleBasic> getContractVehicleList() {
        return contractVehicleList;
    }

    public void setContractVehicleList(List<VehicleBasic> contractVehicleList) {
        this.contractVehicleList = contractVehicleList;
    }
}
