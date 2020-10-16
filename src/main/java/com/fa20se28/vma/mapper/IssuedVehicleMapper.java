package com.fa20se28.vma.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface IssuedVehicleMapper {
    @Insert("INSERT INTO issued_vehicle(vehicle_id, driver_id, issued_date, returned_date) " +
            "VALUES (#{iv_vehicle_id}, #{iv_driver_id}, #{iv_issued_date}, NULL)")
    int assignVehicle(
            @Param("iv_vehicle_id") String vehicleId,
            @Param("iv_driver_id") String driverId,
            @Param("iv_issued_date") String issuedDate);

    @Update("UPDATE issued_vehicle " +
            "SET returned_date = #{iv_returned_date} " +
            "WHERE vehicle_id = #{iv_vehicle_id} ")
    int updateIssuedVehicle(
            @Param("iv_vehicle_id") String vehicleId,
            @Param("iv_returned_date") String returnedDate);
}
