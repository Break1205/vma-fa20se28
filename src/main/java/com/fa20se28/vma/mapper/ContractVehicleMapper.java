package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.VehicleRecommendationReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContractVehicleMapper {
    @Insert("INSERT INTO contract_vehicles " +
            "(contract_id, " +
            "issued_vehicle_id, " +
            "contract_vehicle_status, " +
            "create_date) " +
            "VALUES " +
            "(#{cv_id}, " +
            "#{cv_iv_id}, " +
            "#{cv_status}, " +
            "getDate()) ")
    int assignVehicleForContract(
            @Param("cv_id") int contractId,
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

    @Select("SELECT cv.contract_vehicle_status " +
            "FROM contract_vehicles cv " +
            "WHERE issued_vehicle_id = #{iv_id} " +
            "AND contract_id = #{cv_id} ")
    ContractVehicleStatus getVehicleStatus(
            @Param("cv_id") int contractId,
            @Param("iv_id") int issuedVehicleId);

    @Update("UPDATE contract_vehicles " +
            "SET contract_vehicle_status = #{cv_status} " +
            "WHERE issued_vehicle_id = #{iv_id} " +
            "AND contract_id = #{cv_cid} ")
    int updateContractedVehicleStatus(
            @Param("cv_cid") int contractId,
            @Param("iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

    @Select("SELECT cv.contract_vehicle_id, cv.contract_vehicle_status, v.vehicle_id, vt.vehicle_type_id, vt.vehicle_type_name, v.seats " +
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
            @Result(property = "seats", column = "seats"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status")
    })
    List<VehicleBasic> getContractVehicles(@Param("cv_id") int contractId);

    @Select({"<script> " +
            "SELECT " +
            "cv.contract_id, cv.contract_vehicle_id, cv.contract_vehicle_status " +
            "FROM contract_vehicles cv " +
            "JOIN contract c ON cv.contract_id = c.contract_id " +
            "WHERE cv.issued_vehicle_id = #{cv_iv_id} " +
            "<if test = \"cv_status != null\" > " +
            "AND cv.contract_vehicle_status = #{cv_status} " +
            "</if> " +
            "ORDER BY cv.create_date DESC " +
            "OFFSET ${cv_offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "tripsResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractVehicleId", column = "contract_vehicle_id"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status"),
            @Result(property = "contractTrips", column = "contract_id", many = @Many(select = "getContractTrips")),
    })
    List<Trip> getVehicleTrips(
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus,
            @Param("cv_offset") int offset);

    @Select("SELECT " +
            "contract_detail_id, departure_time, destination_time, departure_location, destination_location " +
            "FROM contract_detail " +
            "WHERE " +
            "contract_id = #{c_id} ")
    @Results(id = "contractTrips", value = {
            @Result(property = "contractTripId", column = "contract_detail_id"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "locations", column = "contract_detail_id", many = @Many(select = "getContractTripSchedule"))
    })
    List<ContractTrip> getContractTrips(@Param("c_id") int contractId);

    @Select("SELECT " +
            "contract_detail_schedule_id, location " +
            "FROM contract_detail_schedule " +
            "WHERE " +
            "contract_detail_id = #{cd_id} ")
    @Results(id = "tripSchedule", value = {
            @Result(property = "locationId", column = "contract_detail_schedule_id"),
    })
    List<ContractTripSchedule> getContractTripSchedule(@Param("cd_id") int contractDetailId);

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

    @Select("SELECT COUNT (cv.contract_vehicle_id) " +
            "FROM contract_vehicles cv " +
            "WHERE cv.contract_id = #{cv_id} " +
            "AND cv.contract_vehicle_status = 'COMPLETED' ")
    int getCompletedVehicleCount(@Param("cv_id") int contractId);

    @Select({"<script> " +
            "SELECT DISTINCT v.vehicle_id, v.model, vt.vehicle_type_id, vt.vehicle_type_name, v.seats, v.year_of_manufacture " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN issued_vehicle iv ON iv.vehicle_id = v.vehicle_id " +
            "WHERE " +
            "v.vehicle_status NOT IN " +
            "( " +
            "SELECT DISTINCT vs.vehicle_status " +
            "FROM vehicle vs " +
            "WHERE vs.vehicle_status = 'REJECTED' " +
            "OR vs.vehicle_status = 'PENDING_APPROVAL' " +
            "OR vs.vehicle_status = 'DELETED' " +
            ") " +
            "<if test = \"vr_req.seatsMin != 0\" > " +
            "AND v.seats &gt;= #{vr_req.seatsMin} " +
            "</if> " +
            "<if test = \"vr_req.seatsMax != 0\" > " +
            "AND v.seats &lt;= #{vr_req.seatsMax} " +
            "</if> " +
            "<if test = \"vr_req.vehicleTypeId != 0\" > " +
            "AND vt.vehicle_type_id =  #{vr_req.vehicleTypeId} " +
            "</if> " +
            "AND iv.issued_vehicle_id NOT IN " +
            "( " +
            "SELECT cv.issued_vehicle_id " +
            "FROM contract_vehicles cv " +
            "JOIN [contract] c ON cv.contract_id = c.contract_id " +
            "WHERE c.contract_id IN " +
            "( " +
            "SELECT DISTINCT c.contract_id " +
            "FROM [contract] c " +
            "JOIN contract_detail cd ON c.contract_id = cd.contract_id " +
            "WHERE " +
            "cd.departure_time &lt; (SELECT DATEADD(hour, +6, #{vr_req.endDate})) " +
            "AND cd.destination_time &gt;= (SELECT DATEADD(hour, -6,  #{vr_req.startDate})) " +
            ") " +
            ") " +
            "ORDER BY v.year_of_manufacture DESC " +
            "OFFSET ${r_offset} ROWS " +
            "FETCH NEXT 10 ROWS ONLY " +
            "</script>"})
    @Results(id = "recommendationResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name")
    })
    List<VehicleRecommendation> getRecommendations(
            @Param("vr_req") VehicleRecommendationReq vehicleRecommendationReq,
            @Param("r_offset") int offset);

    @Select({"<script> " +
            "SELECT COUNT(DISTINCT v.vehicle_id) " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN issued_vehicle iv ON iv.vehicle_id = v.vehicle_id " +
            "WHERE " +
            "v.vehicle_status NOT IN " +
            "( " +
            "SELECT DISTINCT vs.vehicle_status " +
            "FROM vehicle vs " +
            "WHERE vs.vehicle_status = 'REJECTED' " +
            "OR vs.vehicle_status = 'PENDING_APPROVAL' " +
            "OR vs.vehicle_status = 'DELETED' " +
            ") " +
            "<if test = \"vr_req.seatsMin != 0\" > " +
            "AND v.seats &gt;= #{vr_req.seatsMin} " +
            "</if> " +
            "<if test = \"vr_req.seatsMax != 0\" > " +
            "AND v.seats &lt;= #{vr_req.seatsMax} " +
            "</if> " +
            "<if test = \"vr_req.vehicleTypeId != 0\" > " +
            "AND vt.vehicle_type_id =  #{vr_req.vehicleTypeId} " +
            "</if> " +
            "AND iv.issued_vehicle_id NOT IN " +
            "( " +
            "SELECT cv.issued_vehicle_id " +
            "FROM contract_vehicles cv " +
            "JOIN [contract] c ON cv.contract_id = c.contract_id " +
            "WHERE c.contract_id IN " +
            "( " +
            "SELECT DISTINCT c.contract_id " +
            "FROM [contract] c " +
            "JOIN contract_detail cd ON c.contract_id = cd.contract_id " +
            "WHERE " +
            "cd.departure_time &lt; (SELECT DATEADD(hour, +6, #{vr_req.endDate})) " +
            "AND cd.destination_time &gt;= (SELECT DATEADD(hour, -6,  #{vr_req.startDate})) " +
            ") " +
            ") " +
            "</script>"})
    int getRecommendationCount(@Param("vr_req") VehicleRecommendationReq vehicleRecommendationReq);
}
