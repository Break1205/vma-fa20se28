package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DriverMapper {
    @Select({"<script>" +
            "SELECT " +
            "u.user_id, " +
            "us.user_status_name, " +
            "u.full_name, " +
            "u.phone_number, " +
            "u.gender, " +
            "u.date_of_birth, " +
            "u.address, " +
            "u.image_link, " +
            "u.base_salary, " +
            "iv.vehicle_id " +
            "FROM " +
            "[user] u " +
            "LEFT JOIN issued_vehicle iv " +
            "ON u.user_id = iv.driver_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 3 " +
            "AND u.user_id = '${user_id}' " +
            "ORDER BY u.user_id ASC " +
            "</script>"})
    @Results(id = "driverDetailResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "userStatusName", column = "user_status_name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
            @Result(property = "address", column = "address"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "baseSalary", column = "base_salary")})
    Optional<DriverDetail> findDriverById(@Param("user_id") String userId);

    @Select({"<script>" +
            "SELECT " +
            "u.user_id," +
            "u.full_name," +
            "u.phone_number," +
            "iv.vehicle_id," +
            "us.user_status_name " +
            "FROM " +
            "[user] u " +
            "LEFT JOIN issued_vehicle iv " +
            "ON u.user_id= iv.driver_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 3 " +
            "<if test = \"DriverPageReq.userId!=null\" > " +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%' " +
            "</if>" +
            "<if test = \"DriverPageReq.fullName!=null\" > " +
            "AND u.full_name LIKE '%${DriverPageReq.fullName}%' " +
            "</if>" +
            "<if test = \"DriverPageReq.phoneNumber!=null\" > " +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%' " +
            "</if>" +
            "<if test = \"DriverPageReq.viewOption == 1\" > " +
            " AND u.user_status_id != 3" +
            "</if>" +
            "<if test = \"DriverPageReq.userStatusId!=null\" > " +
            "AND u.user_status_id = #{DriverPageReq.userStatusId} " +
            "</if>" +
            "ORDER BY u.user_id ASC " +
            "OFFSET ${DriverPageReq.page} ROWS " +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "driverResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "userStatusName", column = "user_status_name")})
    List<Driver> findDriversByUserIdAndFullNameAndPhoneNumberAndUserStatus(@Param("DriverPageReq")DriverPageReq driverPageReq);

    @Select({"<script>" +
            "SELECT count(u.user_id) " +
            "FROM [user] u " +
            "LEFT JOIN issued_vehicle iv " +
            "ON u.user_id= iv.driver_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 3 " +
            "<if test = \"DriverPageReq.userId!=null\" > " +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%' " +
            "</if>" +
            "<if test = \"DriverPageReq.fullName!=null\" > " +
            "AND u.full_name LIKE '%${DriverPageReq.fullName}%' " +
            "</if>" +
            "<if test = \"DriverPageReq.phoneNumber!=null\" > " +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%' " +
            "</if>" +
            "<if test = \"DriverPageReq.viewOption == 1\" > " +
            " AND u.user_status_id != 3" +
            "</if>" +
            "<if test = \"DriverPageReq.userStatusId!=null\" > " +
            "AND u.user_status_id = #{DriverPageReq.userStatusId} " +
            "</if>" +
            "</script>"})
    int findTotalDriversWhenFilter(@Param("DriverPageReq")DriverPageReq driverPageReq);
}
