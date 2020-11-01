package com.fa20se28.vma.mapper;

import org.apache.ibatis.annotations.*;

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
}
