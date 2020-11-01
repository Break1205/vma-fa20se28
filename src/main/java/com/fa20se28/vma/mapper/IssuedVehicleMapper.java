package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.IssuedVehicle;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface IssuedVehicleMapper {
    @Insert("INSERT INTO issued_vehicle(vehicle_id, driver_id, issued_date, returned_date) " +
            "VALUES (#{iv_vehicle_id}, #{iv_driver_id}, getdate(), NULL)")
    int assignVehicle(
            @Param("iv_vehicle_id") String vehicleId,
            @Param("iv_driver_id") String driverId);

    @Update("UPDATE issued_vehicle " +
            "SET returned_date = getdate() " +
            "WHERE vehicle_id = #{v_id} ")
    int withdrawVehicle(
            @Param("v_id") String vehicleId);

    @Select("SELECT \n" +
            "issued_vehicle_id, \n" +
            "vehicle_id, \n" +
            "driver_id, \n" +
            "issued_date, \n" +
            "returned_date\n" +
            "FROM issued_vehicle \n" +
            "WHERE returned_date IS NULL \n" +
            "AND driver_id = '${driver_id}'")
    @Results(id = "issuedVehicleResult", value = {
            @Result(property = "issuedVehicleId", column = "issued_vehicle_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "issuedDate", column = "issued_date"),
            @Result(property = "returnedDate", column = "returned_date"),
    } )
    Optional<IssuedVehicle> checkIfTheDriverIsStillDriving(@Param("driver_id") String driverId);
}
