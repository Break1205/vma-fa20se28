package com.fa20se28.vma.mapper;

import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface VehicleOwnerMapper {
    @Insert("INSERT INTO owner_vehicles(user_id, vehicle_id, start_date, end_date) " +
            "VALUES (#{u_id}, #{v_id}, #{d_start}, NULL) ")
    int createVehicleOwner(
            @Param("u_id") String userId,
            @Param("v_id") String vehicleId,
            @Param("d_start") LocalDate startDate);

    @Update("UPDATE owner_vehicles " +
            "SET end_date = #{d_end} " +
            "WHERE vehicle_id = #{v_id} " +
            "AND end_date IS NULL ")
    int updateVehicleOwner(
            @Param("v_id") String vehicleId,
            @Param("d_end") LocalDate endDate);

    @Select("SELECT TOP 1 user_id " +
            "FROM owner_vehicles " +
            "WHERE vehicle_id = #{v_id} " +
            "AND end_date IS NULL " +
            "ORDER BY ov.start_date DESC ")
    String getCurrentOwnerId(@Param("v_id") String vehicleId);
}
