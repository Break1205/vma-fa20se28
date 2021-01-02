package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.request.VehicleUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleMapper {
    @Select({"<script> " +
            "SELECT COUNT(v.vehicle_id) " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.vehicle_status NOT IN " +
            "(SELECT DISTINCT vs.vehicle_status " +
            "FROM vehicle vs " +
            "WHERE vs.vehicle_status = 'REJECTED' " +
            "OR vs.vehicle_status = 'PENDING_APPROVAL') " +
            "</if> " +
            "<if test = \"v_option == 1\" > " +
            "v.vehicle_status = #{v_request.vehicleStatus} " +
            "</if> " +
            "<if test = \"v_request.vehicleId != null\" > " +
            "AND v.vehicle_id LIKE '%${v_request.vehicleId}%' " +
            "</if> " +
            "<if test = \"v_request.model != null\" > " +
            "AND v.model LIKE '%${v_request.model}%' " +
            "</if> " +
            "<if test = \"v_request.vehicleTypeId != 0\" > " +
            "AND vt.vehicle_type_id = #{v_request.vehicleTypeId} " +
            "</if> " +
            "<if test = \"v_request.seatsMin != 0\" > " +
            "AND vs.seats &gt;= #{v_request.seatsMin} " +
            "</if> " +
            "<if test = \"v_request.seatsMax != 0\" > " +
            "AND vs.seats &lt;= #{v_request.seatsMax} " +
            "</if> " +
            "<if test = \"v_request.vehicleMinDis != 0\" > " +
            "AND v.distance_driven &gt;= #{v_request.vehicleMinDis} " +
            "</if> " +
            "<if test = \"v_request.vehicleMaxDis != 0\" > " +
            "AND v.distance_driven &lt;= #{v_request.vehicleMaxDis} " +
            "</if> " +
            "<if test = \"v_owner_id != null\" > " +
            "AND v.owner_id LIKE '%${v_owner_id}%' " +
            "</if> " +
            "</script>"})
    int getTotal(
            @Param("v_request") VehiclePageReq request,
            @Param("v_option") int useStatus,
            @Param("v_owner_id") String ownerId);

    @Select({"<script> " +
            "SELECT v.vehicle_id, v.model, vt.vehicle_type_id, vt.vehicle_type_name, vs.seats, v.vehicle_status, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN vehicle_seat vs ON v.seats_id = vs.seats_id " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.vehicle_status NOT IN " +
            "(SELECT DISTINCT vs.vehicle_status " +
            "FROM vehicle vs " +
            "WHERE vs.vehicle_status = 'REJECTED' " +
            "OR vs.vehicle_status = 'PENDING_APPROVAL') " +
            "</if> " +
            "<if test = \"v_option == 1\" > " +
            "v.vehicle_status = #{v_request.vehicleStatus} " +
            "</if> " +
            "<if test = \"v_request.vehicleId != null\" > " +
            "AND v.vehicle_id LIKE '%${v_request.vehicleId}%' " +
            "</if> " +
            "<if test = \"v_request.model != null\" > " +
            "AND v.model LIKE '%${v_request.model}%' " +
            "</if> " +
            "<if test = \"v_request.vehicleTypeId != 0\" > " +
            "AND vt.vehicle_type_id = #{v_request.vehicleTypeId} " +
            "</if> " +
            "<if test = \"v_request.seatsMin != 0\" > " +
            "AND vs.seats &gt;= #{v_request.seatsMin} " +
            "</if> " +
            "<if test = \"v_request.seatsMax != 0\" > " +
            "AND vs.seats &lt;= #{v_request.seatsMax} " +
            "</if> " +
            "<if test = \"v_request.vehicleMinDis != 0\" > " +
            "AND v.distance_driven &gt;= #{v_request.vehicleMinDis} " +
            "</if> " +
            "<if test = \"v_request.vehicleMaxDis != 0\" > " +
            "AND v.distance_driven &lt;= #{v_request.vehicleMaxDis} " +
            "</if> " +
            "<if test = \"v_owner_id != null\" > " +
            "AND v.owner_id LIKE '%${v_owner_id}%' " +
            "</if> " +
            "ORDER BY v.date_of_registration DESC " +
            "OFFSET ${v_offset} ROWS " +
            "<if test = \"v_take_all != 1\" > " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</if> " +
            "</script> "})
    @Results(id = "vehicles", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "vehicleStatus", column = "vehicle_status"),
            @Result(property = "distanceDriven", column = "distance_driven"),
    })
    List<Vehicle> getVehicles(
            @Param("v_request") VehiclePageReq request,
            @Param("v_option") int useStatus,
            @Param("v_offset") int offset,
            @Param("v_owner_id") String ownerId,
            @Param("v_take_all") int takeAll);

    @Select("SELECT " +
            "CASE WHEN " +
            "Count(v.vehicle_id) > 0 THEN 1 " +
            "ELSE 0 END Result " +
            "FROM vehicle v " +
            "WHERE v.vehicle_id = #{v_id} ")
    boolean isVehicleExist(@Param("v_id") String vehicleId);

    @Insert("INSERT INTO vehicle " +
            "(vehicle_id, " +
            "vehicle_type_id," +
            "brand_id, " +
            "vehicle_status, " +
            "image_link, " +
            "model, " +
            "origin_of_manufacture, " +
            "chassis_number, " +
            "engine_number, " +
            "year_of_manufacture, " +
            "date_of_registration, " +
            "distance_driven," +
            "seats_id) " +
            "VALUES " +
            "(#{v_request.vehicleId}, " +
            "#{v_request.vehicleTypeId}, " +
            "#{v_request.brandId}, " +
            "#{v_status}, " +
            "#{v_request.imageLink}, " +
            "#{v_request.model}, " +
            "#{v_request.origin}, " +
            "#{v_request.chassisNumber}, " +
            "#{v_request.engineNumber}, " +
            "CAST(CONCAT(#{v_request.yearOfManufacture}, '-01-01') AS date), " +
            "getDate(), " +
            "#{v_request.distanceDriven}, " +
            "#{v_request.seatsId}) ")
    int createVehicle(
            @Param("v_request") VehicleReq vehicleReq,
            @Param("v_status") VehicleStatus status);

    @Update("UPDATE vehicle " +
            "SET " +
            "vehicle_status = 'DELETED' " +
            "WHERE vehicle_id = #{v_id} ")
    int deleteVehicle(@Param("v_id") String vehicleId);

    @Select("SELECT " +
            "v.vehicle_id, vt.vehicle_type_id,  vt.vehicle_type_name, b.brand_id, b.brand_name, " +
            "v.vehicle_status, vs.seats, v.image_link, v.model, v.origin_of_manufacture, " +
            "v.chassis_number, v.engine_number, v.year_of_manufacture, v.date_of_registration, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON vt.vehicle_type_id = v.vehicle_type_id  " +
            "JOIN brand b ON b.brand_id = v.brand_id " +
            "JOIN vehicle_seat vs ON v.seats_id = vs.seats_id " +
            "WHERE " +
            "v.vehicle_id = #{v_id} ")
    @Results(id = "vehicleDetailsResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "brand.brandId", column = "brand_id"),
            @Result(property = "brand.brandName", column = "brand_name"),
            @Result(property = "vehicleStatus", column = "vehicle_status"),
            @Result(property = "seats", column = "seats"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "origin", column = "origin_of_manufacture"),
            @Result(property = "chassisNumber", column = "chassis_number"),
            @Result(property = "engineNumber", column = "engine_number"),
            @Result(property = "yearOfManufacture", column = "year_of_manufacture"),
            @Result(property = "dateOfRegistration", column = "date_of_registration"),
            @Result(property = "distanceDriven", column = "distance_driven"),
            @Result(property = "owner", column = "vehicle_id", one = @One(select = "getVehicleOwner")),
            @Result(property = "assignedDriver", column = "vehicle_id", one = @One(select = "getAssignedDriver")),
            @Result(property = "values", column = "vehicle_id", many = @Many(select = "getVehicleValues"))
    })
    VehicleDetail getVehicleDetails(@Param("v_id") String vehicleId);

    @Select("SELECT TOP 1 u.[user_id], u.full_name " +
            "FROM owner_vehicles ov " +
            "JOIN [user] u ON u.[user_id] = ov.user_id " +
            "WHERE ov.vehicle_id = #{v_id} " +
            "AND end_date IS NULL " +
            "ORDER BY ov.start_date DESC ")
    @Results(id = "vehicleOwner", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "full_name")
    })
    UserBasic getVehicleOwner(@Param("v_id") String vehicleId);

    @Select("SELECT TOP 1 u.[user_id], u.full_name " +
            "FROM issued_vehicle iv " +
            "JOIN [user] u ON u.[user_id] = iv.driver_id " +
            "WHERE iv.vehicle_id = #{v_id} " +
            "AND iv.returned_date IS NULL " +
            "ORDER BY iv.create_date DESC")
    @Results(id = "assignedDriver", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "full_name")
    })
    UserBasic getAssignedDriver(@Param("v_id") String vehicleId);

    @Select("SELECT " +
            "vehicle_value_id, " +
            "value, " +
            "start_date," +
            "end_date, " +
            "is_deleted " +
            "FROM vehicle_value " +
            "WHERE vehicle_id = #{v_id} " +
            "ORDER BY create_date DESC ")
    @Results(id = "vehicleValues", value = {
            @Result(property = "vehicleValueId", column = "vehicle_value_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "isDeleted", column = "is_deleted")
    })
    List<VehicleValue> getVehicleValues(@Param("v_id") String vehicleId);

    @Select("SELECT " +
            "v.vehicle_status " +
            "FROM vehicle v " +
            "WHERE " +
            "v.vehicle_id = #{v_id} ")
    VehicleStatus getVehicleStatus(@Param("v_id") String vehicleId);

    @Select("SELECT \n" +
            "vehicle_id,\n" +
            "vehicle_status\n" +
            "FROM vehicle\n" +
            "WHERE owner_id = '${user_id}' \n" +
            "AND vehicle_status != 'DELETED'")
    @Results(id = "notDeletedVehicleResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleStatus", column = "vehicle_status")
    })
    List<Vehicle> getNotDeletedVehiclesByOwnerId(@Param("user_id") String userId);

    @Update("UPDATE vehicle " +
            "SET " +
            "vehicle_type_id = #{v_request.vehicleTypeId}, " +
            "brand_id = #{v_request.brandId}, " +
            "seats_id = #{v_request.seatsId}, " +
            "image_link = #{v_request.imageLink}, " +
            "model = #{v_request.model}, " +
            "origin_of_manufacture = #{v_request.origin}, " +
            "chassis_number = #{v_request.chassisNumber}, " +
            "engine_number = #{v_request.engineNumber}, " +
            "year_of_manufacture = CAST(CONCAT(#{v_request.yearOfManufacture}, '-01-01') AS DATE), " +
            "distance_driven = #{v_request.distanceDriven} " +
            "WHERE " +
            "vehicle_id = #{v_request.vehicleId} ")
    int updateVehicle(@Param("v_request") VehicleUpdateReq vehicleUpdateReq);

    @Update("UPDATE vehicle " +
            "SET " +
            "vehicle_status = #{v_status} " +
            "WHERE " +
            "vehicle_id = #{v_id} ")
    int updateVehicleStatus(
            @Param("v_id") String vehicleId,
            @Param("v_status") VehicleStatus vehicleStatus);

    @Insert("INSERT INTO vehicle_log " +
            "(vehicle_id, " +
            "vehicle_type_id," +
            "brand_id, " +
            "owner_id, " +
            "seats, " +
            "image_link, " +
            "model, " +
            "origin_of_manufacture, " +
            "chassis_number, " +
            "engine_number, " +
            "year_of_manufacture, " +
            "date_of_registration, " +
            "distance_driven," +
            "request_id) " +
            "VALUES " +
            "(#{v_rejected.vehicleId}, " +
            "#{v_rejected.vehicleType.vehicleTypeId}, " +
            "#{v_rejected.brand.brandId}, " +
            "#{v_rejected.owner.userId}, " +
            "#{v_rejected.seats}, " +
            "#{v_rejected.imageLink}, " +
            "#{v_rejected.model}, " +
            "#{v_rejected.origin}, " +
            "#{v_rejected.chassisNumber}, " +
            "#{v_rejected.engineNumber}, " +
            "#{v_rejected.yearOfManufacture}, " +
            "#{v_rejected.dateOfRegistration}, " +
            "#{v_rejected.distanceDriven}," +
            "#{r_id}) ")
    int moveDeniedVehicleToLog(
            @Param("v_rejected") VehicleDetail vehicle,
            @Param("r_id") int requestId);

    @Select("SELECT vt.vehicle_type_name, COUNT(v.vehicle_id) AS total " +
            "FROM vehicle v " +
            "RIGHT JOIN vehicle_type vt ON vt.vehicle_type_id = v.vehicle_type_id " +
            "AND v.owner_id = #{v_owner_id} " +
            "GROUP BY vt.vehicle_type_name ")
    @Results(id = "typeCount", value = {
            @Result(property = "typeName", column = "vehicle_type_name"),
            @Result(property = "typeCount", column = "total")
    })
    List<VehicleTypeCount> getTypeCount(@Param("v_owner_id") String ownerId);

    @Select("SELECT v.vehicle_status, COUNT(v.vehicle_id) AS total " +
            "FROM vehicle v " +
            "WHERE v.owner_id = #{v_owner_id} " +
            "GROUP BY v.vehicle_status ")
    @Results(id = "statusCount", value = {
            @Result(property = "statusName", column = "vehicle_status"),
            @Result(property = "statusCount", column = "total")
    })
    List<VehicleStatusCount> getStatusCount(@Param("v_owner_id") String ownerId);

    @Select("SELECT v.vehicle_status " +
            "FROM vehicle v " +
            "WHERE v.owner_id = #{v_owner_id} " +
            "GROUP BY v.vehicle_status ")
    List<VehicleStatus> getStatusInFleet(@Param("v_owner_id") String ownerId);

    @Select("SELECT COUNT(vehicle_id) " +
            "FROM vehicle " +
            "WHERE owner_id = #{v_owner_id} ")
    int getTotalVehicle(@Param("v_owner_id") String ownerId);


}
