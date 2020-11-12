package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleDetail;
import com.fa20se28.vma.model.VehicleDropDown;
import com.fa20se28.vma.request.VehicleDropDownReq;
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
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.vehicle_status != '%DELETED%' " +
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
            "AND v.seats &gt;= #{v_request.seatsMin} " +
            "</if> " +
            "<if test = \"v_request.seatsMax != 0\" > " +
            "AND v.seats &lt;= #{v_request.seatsMax} " +
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
            @Param("v_option") int viewOption,
            @Param("v_owner_id") String ownerId);

    @Select({"<script> " +
            "SELECT v.vehicle_id, v.model, vt.vehicle_type_name, v.seats, v.vehicle_status, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.vehicle_status != '%DELETED%' " +
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
            "AND v.seats &gt;= #{v_request.seatsMin} " +
            "</if> " +
            "<if test = \"v_request.seatsMax != 0\" > " +
            "AND v.seats &lt;= #{v_request.seatsMax} " +
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
            @Result(property = "vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "vehicleStatus", column = "vehicle_status"),
            @Result(property = "vehicleDistance", column = "distance_driven"),
    })
    List<Vehicle> getVehicles(
            @Param("v_request") VehiclePageReq request,
            @Param("v_option") int viewOption,
            @Param("v_offset") int offset,
            @Param("v_owner_id") String ownerId,
            @Param("v_take_all") int takeAll);

    @Select({"<script> " +
            "SELECT v.vehicle_id, v.model, vt.vehicle_type_name, v.seats " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "WHERE " +
            "v.vehicle_status IN " +
            "(SELECT v.vehicle_status " +
            "FROM vehicle v " +
            "Where v.vehicle_status = 'AVAILABLE' OR v.vehicle_status = 'AVAILABLE_NO_DRIVER') " +
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
            "AND v.seats &gt;= #{v_request.seatsMin} " +
            "</if> " +
            "<if test = \"v_request.seatsMax != 0\" > " +
            "AND v.seats &lt;= #{v_request.seatsMax} " +
            "</if> " +
            "<if test = \"v_owner_id != null\" > " +
            "AND v.owner_id LIKE '%${v_owner_id}%' " +
            "</if> " +
            "AND v.vehicle_id NOT IN " +
            "(SELECT TOP 1 iv.vehicle_id " +
            "FROM issued_vehicle iv " +
            "WHERE iv.returned_date IS NULL) " +
            "ORDER BY v.date_of_registration DESC " +
            "OFFSET ${v_offset} ROWS " +
            "FETCH NEXT 10 ROWS ONLY " +
            "</script> "})
    @Results(id = "vehiclesDropDown", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleTypeName", column = "vehicle_type_name")
    })
    List<VehicleDropDown> getVehicleDropDownByStatus(
            @Param("v_request") VehicleDropDownReq request,
            @Param("v_offset") int offset,
            @Param("v_owner_id") String ownerId);

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
            "owner_id, " +
            "vehicle_status, " +
            "seats, " +
            "image_link, " +
            "model, " +
            "origin_of_manufacture, " +
            "chassis_number, " +
            "engine_number, " +
            "year_of_manufacture, " +
            "date_of_registration, " +
            "distance_driven) " +
            "VALUES " +
            "(#{v_request.vehicleId}, " +
            "#{v_request.vehicleTypeId}, " +
            "#{v_request.brandId}, " +
            "#{v_request.ownerId}, " +
            "#{v_status}, " +
            "#{v_request.seats}, " +
            "#{v_request.imageLink}, " +
            "#{v_request.model}, " +
            "#{v_request.origin}, " +
            "#{v_request.chassisNumber}, " +
            "#{v_request.engineNumber}, " +
            "#{v_request.yearOfManufacture}, " +
            "getdate(), " +
            "#{v_request.distanceDriven}) ")
    int createVehicle(
            @Param("v_request") VehicleReq vehicleReq,
            @Param("v_status") VehicleStatus status);

    @Update("UPDATE vehicle " +
            "SET " +
            "vehicle_status = 'DELETED' " +
            "WHERE vehicle_id = #{v_id} ")
    int deleteVehicle(@Param("v_id") String vehicleId);

    @Select("SELECT " +
            "v.vehicle_id, vt.vehicle_type_id,  vt.vehicle_type_name, b.brand_id, b.brand_name, u.[user_id], u.full_name, " +
            "v.vehicle_status, v.seats, v.image_link, v.model, v.origin_of_manufacture, " +
            "v.chassis_number, v.engine_number, v.year_of_manufacture, v.date_of_registration, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON vt.vehicle_type_id = v.vehicle_type_id  " +
            "JOIN brand b ON b.brand_id = v.brand_id " +
            "JOIN [user] u ON u.user_id = v.owner_id " +
            "WHERE " +
            "v.vehicle_id = #{v_id} ")
    @Results(id = "vehicleDetails", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "brand.brandId", column = "brand_id"),
            @Result(property = "brand.brandName", column = "brand_name"),
            @Result(property = "owner.userId", column = "user_id"),
            @Result(property = "owner.userName", column = "full_name"),
            @Result(property = "vehicleStatus", column = "vehicle_status"),
            @Result(property = "seats", column = "seats"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "origin", column = "origin_of_manufacture"),
            @Result(property = "chassisNumber", column = "chassis_number"),
            @Result(property = "engineNumber", column = "engine_number"),
            @Result(property = "yearOfManufacture", column = "year_of_manufacture"),
            @Result(property = "dateOfRegistration", column = "date_of_registration"),
            @Result(property = "distanceDriven", column = "distance_driven"),
    })
    VehicleDetail getVehicleDetails(@Param("v_id") String vehicleId);

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
            "owner_id = #{v_request.ownerId}, " +
            "vehicle_status = #{v_request.vehicleStatus}, " +
            "seats = #{v_request.seats}, " +
            "image_link = #{v_request.imageLink}, " +
            "model = #{v_request.model}, " +
            "origin_of_manufacture = #{v_request.origin}, " +
            "chassis_number = #{v_request.chassisNumber}, " +
            "engine_number = #{v_request.engineNumber}, " +
            "year_of_manufacture = #{v_request.yearOfManufacture}, " +
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
}
