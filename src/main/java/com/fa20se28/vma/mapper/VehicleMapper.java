package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleDropDown;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleMapper {
    @Select({"<script> " +
            "SELECT COUNT(v.vehicle_id) " +
            "FROM vehicle v " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.is_deleted = 0 " +
            "</if> " +
            "<if test = \"v_option == 1\" > " +
            "v.is_deleted = 1 " +
            "</if> " +
            "<if test = \"v_owner_id != null\" > " +
            "AND v.owner_id LIKE '%${v_owner_id}%' " +
            "</if> " +
            "</script>"})
    int getTotal(@Param("v_option") int viewOption,
                 @Param("v_owner_id") String ownerId);

    @Select({"<script> " +
            "SELECT v.vehicle_id, v.model, vt.vehicle_type_name , vs.vehicle_status_name, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN vehicle_status vs ON v.vehicle_status_id = vs.vehicle_status_id " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.is_deleted = 0 " +
            "</if> " +
            "<if test = \"v_option == 1\" > " +
            "v.is_deleted = 1 " +
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
            "<if test = \"v_request.vehicleStatusId != 0\" > " +
            "AND vs.vehicle_status_id = #{v_request.vehicleStatusId} " +
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
            "FETCH NEXT 15 ROWS ONLY " +
            "</script> "})
    @Results(id = "vehicles", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "vehicleStatusName", column = "vehicle_status_name"),
            @Result(property = "vehicleDistance", column = "distance_driven"),
    })
    List<Vehicle> getVehicles(
            @Param("v_request") VehiclePageReq request,
            @Param("v_option") int viewOption,
            @Param("v_offset") int offset,
            @Param("v_owner_id") String ownerId);

    @Select({"<script> " +
            "SELECT v.vehicle_id, v.model, vt.vehicle_type_name " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "WHERE " +
            "v.is_deleted = 0 AND v.vehicle_status_id = 1 " +
            "<if test = \"v_request.vehicleId != null\" > " +
            "AND v.vehicle_id LIKE '%${v_request.vehicleId}%' " +
            "</if> " +
            "<if test = \"v_request.model != null\" > " +
            "AND v.model LIKE '%${v_request.model}%' " +
            "</if> " +
            "<if test = \"v_request.vehicleTypeName != null\" > " +
            "AND vt.vehicle_type_name LIKE '%${v_request.vehicleTypeName}%' " +
            "</if> " +
            "<if test = \"v_owner_id != null\" > " +
            "AND v.owner_id LIKE '%${v_owner_id}%' " +
            "</if> " +
            "AND v.vehicle_id NOT IN " +
            "(SELECT iv.vehicle_id " +
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
    List<VehicleDropDown> getAvailableVehicles(
            @Param("v_request") VehicleDropDownReq request,
            @Param("v_offset") int offset,
            @Param("v_owner_id") String ownerId);

}
