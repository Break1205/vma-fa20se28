package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Vehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VehicleMapper {
    @Select("SELECT COUNT(vehicle_id) " +
            "FROM vehicle")
    int getTotal();

    @Select("SELECT v.vehicle_id, v.model, v.vehicle_id , vt.vehicle_type_name, v.vehicle_status_id, vs.vehicle_status_name, v.distance_driven " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN vehicle_status vs ON v.vehicle_status_id = vs.vehicle_status_id " +
            "ORDER BY v.date_of_registration DESC " +
            "OFFSET 0 ROWS " +
            "FETCH NEXT 15 ROWS ONLY")
    @Results(id = "vehicles", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType.vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleType.vehicleTypeName", column = "vehicle_type_name"),
            @Result(property = "vehicleStatus.vehicleStatusId", column = "vehicle_status_id"),
            @Result(property = "vehicleStatus.vehicleStatusName", column = "vehicle_status_name"),
    })
    List<Vehicle> getVehicles();
}
