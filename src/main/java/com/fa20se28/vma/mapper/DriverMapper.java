package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.DriverRes;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DriverMapper {
    @Select({"<script>" +
            "SELECT " +
            "u.user_id, " +
            "u.user_status, " +
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
            @Result(property = "userStatus", column = "user_status"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
            @Result(property = "address", column = "address"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "baseSalary", column = "base_salary")})
    Optional<DriverDetail> findDriverById(@Param("user_id") String userId);

    @Select({"<script>" +
            "SELECT\n" +
            "u.user_id, \n" +
            "u.full_name, \n" +
            "u.phone_number, \n" +
            "iv.vehicle_id, \n" +
            "u.user_status\n" +
            "FROM\n" +
            "[user] u\n" +
            "LEFT JOIN issued_vehicle iv\n" +
            "ON u.user_id= iv.driver_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 3\n" +
            "AND iv.returned_date IS NULL \n" +
            "<if test = \"DriverPageReq.userId!=null\" >\n" +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${DriverPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.userStatus!=null\" >\n" +
            "AND u.user_status = #{DriverPageReq.userStatus}\n" +
            "</if> \n" +
            "ORDER BY u.user_id ASC\n" +
            "OFFSET ${DriverPageReq.page} ROWS\n" +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "driverResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "userStatus", column = "user_status")})
    List<DriverRes> findDrivers(@Param("DriverPageReq") DriverPageReq driverPageReq);

    @Select({"<script>" +
            "SELECT count(u.user_id)\n" +
            "FROM [user] u\n" +
            "LEFT JOIN issued_vehicle iv\n" +
            "ON u.user_id= iv.driver_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 3\n" +
            "AND iv.returned_date IS NULL \n" +
            "<if test = \"DriverPageReq.userId!=null\" >\n" +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${DriverPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.userStatus!=null\" >\n" +
            "AND u.user_status = #{DriverPageReq.userStatus}\n" +
            "</if> " +
            "</script>"})
    int findTotalDriversWhenFilter(@Param("DriverPageReq") DriverPageReq driverPageReq);
}
