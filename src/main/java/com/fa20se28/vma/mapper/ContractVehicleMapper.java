package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.model.VehicleBasic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContractVehicleMapper {
    @Insert("INSERT INTO contract_vehicles " +
            "(contract_id, " +
            "issued_vehicle_id, " +
            "contract_vehicle_status) " +
            "VALUES " +
            "(#{cv_id}, " +
            "#{cv_iv_id}, " +
            "#{cv_status}) ")
    int assignVehicleForContract(
            @Param("cv_id") int contractId,
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

    @Update("")
    int updateContractedVehicleInfo();

    @Update("")
    int updateContractedVehicleStatus();

    @Select("SELECT cv.contract_vehicle_id, v.vehicle_id, vt.vehicle_type_id, vt.vehicle_type_name, v.seats " +
            "FROM contract_vehicles cv " +
            "JOIN issued_vehicle iv ON cv.issued_vehicle_id = iv.issued_vehicle_id " +
            "JOIN vehicle v ON iv.vehicle_id = v.vehicle_id " +
            "JOIN vehicle_type vt ON vt.vehicle_type_id = v.vehicle_type_id " +
            "WHERE cv.contract_id = #{cv_id} ")
    @Results(id = "contractVehicleResult", value = {
            @Result(property = "contractVehicleId", column = "contract_vehicle_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "seats", column = "seats")
    })
    List<VehicleBasic> getContractVehicles(@Param("cv_id") int contractId);
}
