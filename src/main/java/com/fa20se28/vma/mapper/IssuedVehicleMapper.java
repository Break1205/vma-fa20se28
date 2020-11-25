package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.DriverHistory;
import com.fa20se28.vma.model.IssuedVehicle;
import com.fa20se28.vma.model.UserBasic;
import com.fa20se28.vma.model.AssignedVehicle;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IssuedVehicleMapper {
    @Insert("INSERT INTO issued_vehicle(vehicle_id, driver_id, issued_date, returned_date, create_date) " +
            "VALUES (#{iv_vehicle_id}, NULL, NULL, NULL, getdate()) ")
    int createPlaceholder(@Param("iv_vehicle_id") String vehicleId);

    @Update({"<script>" +
            "UPDATE issued_vehicle " +
            "SET " +
            "<if test = \"iv_option == 0\" > " +
            "driver_id = #{iv_driver_id}, " +
            "issued_date = getdate() " +
            "WHERE vehicle_id = #{v_id} " +
            "AND issued_date IS NULL " +
            "</if> " +
            "<if test = \"iv_option == 1\" > " +
            "returned_date = getdate() " +
            "WHERE vehicle_id = #{v_id} " +
            "AND issued_date IS NOT NULL " +
            "AND returned_date IS NULL " +
            "</if> " +
            "</script>"})
    int updateIssuedVehicle(
            @Param("v_id") String vehicleId,
            @Param("iv_driver_id") String driverId,
            @Param("iv_option") int option);

    @Select("SELECT TOP 1 " +
            "CASE WHEN " +
            "iv.returned_date IS NULL " +
            "AND iv.driver_id IS NOT NULL THEN 1 " +
            "ELSE 0 END Result " +
            "FROM issued_vehicle iv " +
            "WHERE iv.vehicle_id = #{v_id} " +
            "ORDER BY iv.create_date DESC")
    boolean isVehicleHasDriver(@Param("v_id") String vehicleId);

    // Unused
    @Select("SELECT TOP 1 " +
            "CASE WHEN " +
            "iv.returned_date IS NULL THEN 1 " +
            "ELSE 0 END Result " +
            "FROM issued_vehicle iv " +
            "WHERE iv.driver_id = #{dr_id} " +
            "ORDER BY iv.create_date DESC ")
    boolean isDriverAlreadyAssigned(@Param("dr_id") String driverId);

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
            @Result(property = "returnedDate", column = "returned_date")
    })
    Optional<IssuedVehicle> checkIfTheDriverIsStillDriving(@Param("driver_id") String driverId);

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

    @Select("SELECT u.[user_id], u.full_name, iv.issued_date, iv.returned_date " +
            "FROM issued_vehicle iv " +
            "JOIN [user] u ON u.[user_id] = iv.driver_id " +
            "WHERE iv.vehicle_id = #{v_id} ")
    @Results(id = "driverHistory", value = {
            @Result(property = "driver.userId", column = "user_id"),
            @Result(property = "driver.userName", column = "full_name"),
            @Result(property = "issuedDate", column = "issued_date"),
            @Result(property = "returnedDate", column = "returned_date")
    })
    List<DriverHistory> getDriverHistory(@Param("v_id") String vehicleId);

    @Select("SELECT TOP 1 iv.issued_vehicle_id " +
            "FROM issued_vehicle iv " +
            "WHERE iv.vehicle_id = #{v_id} " +
            "ORDER BY iv.create_date DESC ")
    int getCurrentIssuedVehicleId(@Param("v_id") String vehicleId);

    @Select("SELECT iv.issued_vehicle_id, iv.vehicle_id, iv.issued_date, iv.returned_date " +
            "FROM issued_vehicle iv " +
            "WHERE iv.driver_id = #{dv_id} " +
            "AND iv.returned_date IS NULL ")
    @Results(id = "assignedVehicleResult", value = {
            @Result(property = "issuedVehicleId", column = "issued_vehicle_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "issuedDate", column = "issued_date"),
            @Result(property = "returnedDate", column = "returned_date")
    })
    AssignedVehicle getCurrentlyAssignedVehicleByDriverId(@Param("dv_id") String driverId);

    @Select("SELECT iv.issued_vehicle_id, iv.vehicle_id, iv.issued_date, iv.returned_date " +
            "FROM issued_vehicle iv " +
            "WHERE iv.driver_id = #{dv_id} " +
            "ORDER BY issued_date DESC ")
    @ResultMap("assignedVehicleResult")
    List<AssignedVehicle> getVehicleHistory(@Param("dv_id") String driverId);
}
