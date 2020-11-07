package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.request.ContractVehicleReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ContractVehicleMapper {
    @Insert("INSERT INTO vehicle " +
            "(contract_id, " +
            "issued_vehicle_id, " +
            "contract_vehicle_status) " +
            "VALUES " +
            "(#{cv_request.contractId}, " +
            "#{cv_request.yearOfManufacture}, " +
            "#{cv_status}) ")
    int assignVehicleForContract(
            @Param("cv_request") ContractVehicleReq contractVehicleReq,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

    @Update("")
    int updateContractedVehicleInfo();

}
