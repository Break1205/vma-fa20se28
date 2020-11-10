package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.model.Trip;
import com.fa20se28.vma.model.VehicleBasic;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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

    @Update("UPDATE contract_vehicles " +
            "SET contract_vehicle_status = #{cv_status} " +
            "WHERE contract_vehicle_id = #{cv_cid} ")
    int updateContractedVehicleStatus(
            @Param("cv_cid") int contractVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

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

    @Select({"<script> " +
            "SELECT cv.contract_id, cv.contract_vehicle_id, cv.contract_vehicle_status, c.departure_time, c.destination_time " +
            "FROM contract_vehicles cv " +
            "JOIN contract c ON cv.contract_id = c.contract_id " +
            "WHERE cv.issued_vehicle_id = #{cv_iv_id} " +
            "<if test = \"cv_dep_time != null\" > " +
            "AND c.departure_time &gt;= #{cv_dep_time} " +
            "</if> " +
            "<if test = \"cv_des_time != null\" > " +
            "AND c.destination_time &lt;= #{cv_des_time} " +
            "</if> " +
            "</script>"})
    @Results(id = "tripsResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractVehicleId", column = "contract_vehicle_id"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status")
    })
    List<Trip> getVehicleTrips(
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_dep_time") Date departureTime,
            @Param("cv_des_time") Date destinationTime);

    @Select("SELECT " +
            "CASE WHEN " +
            "COUNT(cv.contract_vehicle_id) > 0 THEN 1 " +
            "ELSE 0 END Result " +
            "FROM contract_vehicles cv " +
            "JOIN contract c ON cv.contract_id = c.contract_id " +
            "WHERE cv.issued_vehicle_id = #{cv_iv_id} " +
            "AND cv.contract_id = #{cv_id} ")
    boolean checkIfVehicleIsAlreadyAssignedToContract(
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_id") int contractId);
}
