package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.VehicleContractReq;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ContractVehicleMapper {
    @Insert("INSERT INTO contract_vehicles " +
            "(contract_trip_id, " +
            "issued_vehicle_id, " +
            "contract_vehicle_status, " +
            "create_date) " +
            "VALUES " +
            "(#{cv_detail_id}, " +
            "#{cv_iv_id}, " +
            "#{cv_status}, " +
            "getDate()) ")
    int assignVehicleForContract(
            @Param("cv_detail_id") int contractTripId,
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

    @Select("SELECT cv.contract_vehicle_status " +
            "FROM contract_vehicles cv " +
            "WHERE issued_vehicle_id = #{iv_id} " +
            "AND contract_trip_id = #{cd_id} ")
    ContractVehicleStatus getVehicleStatus(
            @Param("cd_id") int contractTripId,
            @Param("iv_id") int issuedVehicleId);

    @Update("UPDATE contract_vehicles " +
            "SET contract_vehicle_status = #{cv_status} " +
            "WHERE issued_vehicle_id = #{iv_id} " +
            "AND contract_trip_id = #{cd_id} ")
    int updateContractedVehicleStatus(
            @Param("cd_id") int contractTripId,
            @Param("iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus);

    @Select("SELECT cv.contract_vehicle_id,cd.contract_trip_id, cv.contract_vehicle_status, v.vehicle_id, vt.vehicle_type_id, vt.vehicle_type_name, v.seats " +
            "FROM contract_vehicles cv " +
            "JOIN issued_vehicle iv ON cv.issued_vehicle_id = iv.issued_vehicle_id " +
            "JOIN vehicle v ON iv.vehicle_id = v.vehicle_id " +
            "JOIN vehicle_type vt ON vt.vehicle_type_id = v.vehicle_type_id " +
            "JOIN contract_trip cd ON cv.contract_trip_id = cd.contract_trip_id " +
            "JOIN contract c ON c.contract_id = cd.contract_id " +
            "WHERE cd.contract_trip_id = #{cd_id} ")
    @Results(id = "contractVehicleResult", value = {
            @Result(property = "contractVehicleId", column = "contract_vehicle_id"),
            @Result(property = "contractTripId", column = "contract_trip_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "seats", column = "seats"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status")
    })
    List<VehicleBasic> getContractVehicles(@Param("cd_id") int contractTripId);

    @Select({"<script> " +
            "SELECT " +
            "c.contract_id,cd.contract_trip_id, cv.contract_vehicle_id, cv.contract_vehicle_status " +
            "FROM contract_vehicles cv " +
            "JOIN contract_trip cd ON cv.contract_trip_id = cd.contract_trip_id " +
            "JOIN contract c ON cd.contract_id = c.contract_id " +
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
            @Result(property = "contractTrip", column = "contract_trip_id", one = @One(select = "getContractTrip")),
    })
    List<Trip> getVehicleTrips(
            @Param("cv_iv_id") int issuedVehicleId,
            @Param("cv_status") ContractVehicleStatus vehicleStatus,
            @Param("cv_offset") int offset);

    @Select("SELECT " +
            "contract_trip_id, departure_time, destination_time, departure_location, destination_location " +
            "FROM contract_trip " +
            "WHERE " +
            "contract_trip_id = #{contract_trip_id} ")
    @Results(id = "contractTrips", value = {
            @Result(property = "contractTripId", column = "contract_trip_id"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "locations", column = "contract_trip_id", many = @Many(select = "getContractTripSchedule"))
    })
    ContractTrip getContractTrip(@Param("contract_trip_id") int contractTripId);

    @Select("SELECT " +
            "contract_trip_schedule_id, location " +
            "FROM contract_trip_schedule " +
            "WHERE " +
            "contract_trip_id = #{cd_id} ")
    @Results(id = "tripSchedule", value = {
            @Result(property = "locationId", column = "contract_trip_schedule_id"),
    })
    List<ContractTripSchedule> getContractTripSchedule(@Param("cd_id") int contractTripId);

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

    @Select("SELECT COUNT (cv.contract_vehicle_id)\n" +
            "FROM contract_vehicles cv \n" +
            "JOIN contract_trip cd \n" +
            "ON cv.contract_trip_id = cd.contract_trip_id \n" +
            "JOIN contract c \n" +
            "ON cd.contract_id = c.contract_id\n" +
            "WHERE c.contract_id = #{c_id}\n" +
            "AND cv.contract_vehicle_status = 'COMPLETED' \n")
    int getCompletedVehicleCount(@Param("c_id") int contractId);

    @Select({"<script> " +
            "SELECT DISTINCT v.vehicle_id, v.model, vt.vehicle_type_id, vt.vehicle_type_name, vs.seats, v.year_of_manufacture " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN vehicle_seat vs ON vs.seats_id = v.seats_id " +
            "WHERE " +
            "v.vehicle_status NOT IN " +
            "( " +
            "SELECT DISTINCT vs.vehicle_status " +
            "FROM vehicle vs " +
            "WHERE vs.vehicle_status = 'REJECTED' " +
            "OR vs.vehicle_status = 'PENDING_APPROVAL' " +
            "OR vs.vehicle_status = 'DELETED' " +
            "OR vs.vehicle_status = 'AVAILABLE_NO_DRIVER' " +
            ") " +
            "<if test = \"vr_req.seatsMin != 0\" > " +
            "AND vs.seats &gt;= #{vr_req.seatsMin} " +
            "</if> " +
            "<if test = \"vr_req.seatsMax != 0\" > " +
            "AND vs.seats &lt;= #{vr_req.seatsMax} " +
            "</if> " +
            "<if test = \"vr_req.vehicleTypeId != 0\" > " +
            "AND vt.vehicle_type_id =  #{vr_req.vehicleTypeId} " +
            "</if> " +
            "<if test = \"vr_req.yearMin != null\" > " +
            "AND v.year_of_manufacture &gt;= #{vr_req.yearMin} " +
            "</if> " +
            "<if test = \"vr_req.yearMax != null\" > " +
            "AND v.year_of_manufacture &lt;= #{vr_req.yearMax} " +
            "</if> " +
            "AND v.vehicle_id IN " +
            "( " +
            "SELECT iv.vehicle_id " +
            "FROM issued_vehicle iv " +
            "WHERE iv.issued_vehicle_id NOT IN " +
            "( " +
            "SELECT cv.issued_vehicle_id " +
            "FROM contract_vehicles cv " +
            "JOIN contract_trip cd ON cd.contract_trip_id = cv.contract_trip_id " +
            "WHERE cv.contract_trip_id IN " +
            "( " +
            "SELECT cd.contract_trip_id " +
            "FROM contract_trip cd " +
            "WHERE " +
            "cd.departure_time &lt; (SELECT DATEADD(hour, +2, CAST(#{vr_req.endDate} AS datetime) )) " +
            "AND cd.destination_time &gt;= (SELECT DATEADD(hour, -2,  CAST(#{vr_req.startDate} AS datetime) )) " +
            ") " +
            "GROUP BY cv.issued_vehicle_id " +
            ") " +
            "GROUP BY iv.vehicle_id " +
            ") " +
            "ORDER BY v.year_of_manufacture DESC " +
            "</script>"})
    @Results(id = "availableVehiclesResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "yearOfManufacture", column = "year_of_manufacture")
    })
    List<VehicleContract> getAvailableVehicles(@Param("vr_req") VehicleContractReq vehicleContractReq);

    @Delete("DELETE cv FROM contract_vehicles cv\n" +
            "JOIN contract_trip cd \n" +
            "ON cv.contract_trip_id = cd.contract_trip_id \n" +
            "WHERE cd.contract_id = #{c_id}\n")
    int deleteContractVehicles(@Param("c_id") int contractId);

    @Select("SELECT \n" +
            "contract_vehicle_id, \n" +
            "contract_trip_id,\n" +
            "issued_vehicle_id, \n" +
            "contract_vehicle_status\n" +
            "FROM contract_vehicles \n" +
            "WHERE contract_trip_id = #{cd_id}")
    @Results(id = "contractVehicleByContractTripId", value = {
            @Result(property = "contractVehicleId", column = "contract_vehicle_id"),
            @Result(property = "contractTripId", column = "contract_trip_id"),
            @Result(property = "issuedVehicleId", column = "issued_vehicle_id"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status")
    })
    List<ContractVehicle> getContractVehiclesByContractTripId(@Param("cd_id") int contractTripId);

    @Select("SELECT cv.contract_vehicle_id,cv.contract_vehicle_status\n" +
            "FROM contract_vehicles cv \n" +
            "JOIN contract_trip cd \n" +
            "ON cv.contract_trip_id = cd.contract_trip_id \n" +
            "JOIN contract c \n" +
            "ON cd.contract_id = c.contract_id\n" +
            "WHERE c.contract_id = #{c_id} " +
            "AND cv.contract_vehicle_status != 'DROPPED'")
    @Results(id = "ContractVehiclesByContractIdResult", value = {
            @Result(property = "contractVehicleId", column = "contract_vehicle_id"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status")
    })
    List<ContractVehicle> getContractVehiclesByContractId(@Param("c_id") int contractId);

    @Select("SELECT " +
            "CASE WHEN " +
            "COUNT(cv.contract_vehicle_id) > 0 THEN 1 " +
            "ELSE 0 END Result " +
            "FROM contract_vehicles cv " +
            "JOIN contract_trip ct ON ct.contract_trip_id = ct.contract_trip_id " +
            "WHERE cv.issued_vehicle_id = #{iv_id} " +
            "AND (cv.contract_vehicle_status = 'NOT_STARTED' OR cv.contract_vehicle_status = 'IN_PROGRESS') " +
            "AND (ct.destination_time <= #{t_current} " +
            "OR ct.departure_time >= #{t_current}) ")
    boolean checkIfThereIsRemainingTrip(
            @Param("iv_id") int issuedVehicleId,
            @Param("t_current") LocalDateTime currentDateTime);

    @Select("SELECT DISTINCT c.contract_id, c.total_price, q1.total_vehicle " +
            "FROM contract_vehicles cv " +
            "JOIN issued_vehicle iv ON cv.issued_vehicle_id = iv.issued_vehicle_id " +
            "JOIN contract_trip cd ON cv.contract_trip_id = cd.contract_trip_id " +
            "JOIN [contract] c ON cd.contract_id = c.contract_id " +
            "JOIN " +
            "(" +
            "SELECT q.contract_id, SUM(q.total_vehicle) AS total_vehicle " +
            "FROM " +
            "(SELECT COUNT(cv.contract_vehicle_id) AS total_vehicle, cv.contract_trip_id, c.contract_id " +
            "FROM contract_vehicles cv " +
            "JOIN contract_trip cd ON cv.contract_trip_id = cd.contract_trip_id " +
            "JOIN [contract] c ON cd.contract_id = c.contract_id " +
            "GROUP BY cv.contract_trip_id, c.contract_id) q " +
            "GROUP BY q.contract_id " +
            ") q1 ON c.contract_id = q1.contract_id " +
            "WHERE iv.vehicle_id = #{v_id} " +
            "AND cd.destination_time BETWEEN CAST(CONCAT(#{t_first}, ' 00:00:00') AS datetime) AND CAST(CONCAT(#{t_last}, ' 23:59:59') AS datetime) ")
    @Results(id = "vehicleCountResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "totalValue", column = "total_price"),
            @Result(property = "totalVehicles", column = "total_vehicle")
    })
    List<VehicleContractValue> getVehicleCountFromContract(
            @Param("v_id") String vehicleId,
            @Param("t_first") LocalDate firstDate,
            @Param("t_last") LocalDate lastDate);

    @Select("SELECT SUM(q1.occurrence) AS occurrence " +
            "FROM " +
            "( " +
            "SELECT COUNT(cv.contract_vehicle_id) AS occurrence, cv.contract_trip_id, c.contract_id " +
            "FROM contract_vehicles cv " +
            "JOIN contract_trip cd ON cv.contract_trip_id = cd.contract_trip_id " +
            "JOIN [contract] c ON cd.contract_id = c.contract_id " +
            "JOIN issued_vehicle iv ON iv.issued_vehicle_id = cv.issued_vehicle_id " +
            "WHERE iv.vehicle_id = #{v_id} " +
            "GROUP BY cv.contract_trip_id, c.contract_id " +
            ") q1 " +
            "WHERE q1.contract_id = #{c_id} " +
            "GROUP BY q1.contract_id ")
    int getOccurrence(
            @Param("c_id") int contractId,
            @Param("v_id") String vehicleId);
}
