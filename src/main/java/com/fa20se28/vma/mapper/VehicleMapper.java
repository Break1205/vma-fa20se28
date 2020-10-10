package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleStatus;
import com.fa20se28.vma.model.VehicleType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleMapper {
    @Select({"<script> " +
            "SELECT COUNT(v.vehicle_id) " +
            "FROM vehicle v " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.is_deleted = 0 OR v.is_deleted = 1 " +
            "</if> " +
            "<if test = \"v_option == 1\" > " +
            "v.is_deleted = 0 " +
            "</if> " +
            "<if test = \"v_option == 2\" > " +
            "v.is_deleted = 1 " +
            "</if> " +
            "<if test = \"v_owner_id != null\" > " +
            "AND v.owner_id LIKE '%${v_owner_id}%' " +
            "</if> " +
            "</script>"})
    int getTotal(@Param("v_option") int viewOption,
                 @Param("v_owner_id") String ownerId);

    @Select({"<script> " +
            "SELECT v.vehicle_id, v.model, v.vehicle_id , vt.vehicle_type_name, v.vehicle_status_id, vs.vehicle_status_name, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN vehicle_status vs ON v.vehicle_status_id = vs.vehicle_status_id " +
            "WHERE " +
            "<if test = \"v_option == 0\" > " +
            "v.is_deleted = 0 OR v.is_deleted = 1 " +
            "</if> " +
            "<if test = \"v_option == 1\" > " +
            "v.is_deleted = 0 " +
            "</if> " +
            "<if test = \"v_option == 2\" > " +
            "v.is_deleted = 1 " +
            "</if> " +
            "<if test = \"v_id != null\" > " +
            "AND v.vehicle_id LIKE '%${v_id}%' " +
            "</if> " +
            "<if test = \"v_model != null\" > " +
            "AND v.model LIKE '%${v_model}%' " +
            "</if> " +
            "<if test = \"v_type != null\" > " +
            "AND vt.vehicle_type_name LIKE '%${v_type}%' " +
            "</if> " +
            "<if test = \"v_status != null\" > " +
            "AND vs.vehicle_status_name LIKE '%${v_status}%' " +
            "</if> " +
            "<if test = \"v_min != -1\" > " +
            "AND v.distance_driven &gt;= #{v_min} " +
            "</if> " +
            "<if test = \"v_max != -1\" > " +
            "AND v.distance_driven &lt;= #{v_max} " +
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
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "vehicleStatus.vehicleStatusId", column = "vehicle_status_id"),
            @Result(property = "vehicleStatus.vehicleStatusName", column = "vehicle_status_name"),
            @Result(property = "vehicleDistance", column = "distance_driven"),
    })
    List<Vehicle> getVehicles(@Param("v_id") String vehicleId,
                              @Param("v_model") String model,
                              @Param("v_type") String vehicleType,
                              @Param("v_min") float minDistance,
                              @Param("v_max") float maxDistance,
                              @Param("v_status") String vehicleStatus,
                              @Param("v_option") int viewOption,
                              @Param("v_offset") int offset,
                              @Param("v_owner_id") String ownerId);

    @Select("SELECT vt.vehicle_type_id, vt.vehicle_type_name " +
            "FROM vehicle_type vt ")
    @Results(id = "types", value = {
            @Result(property = "vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleTypeName", column = "vehicle_type_name"),
    })
    List<VehicleType> getTypes();

    @Select("SELECT vs.vehicle_status_id, vs.vehicle_status_name " +
            "FROM vehicle_status vs ")
    @Results(id = "status", value = {
            @Result(property = "vehicleStatusId", column = "vehicle_status_id"),
            @Result(property = "vehicleStatusName", column = "vehicle_status_name"),
    })
    List<VehicleStatus> getStatus();
}
