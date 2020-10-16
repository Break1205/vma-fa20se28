package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
            "AND u.user_id LIKE '%${user_id}%' " +
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
            "<if test = \"user_id!=null\" > " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "</if>" +
            "<if test = \"full_name!=null\" > " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "</if>" +
            "<if test = \"phone_number!=null\" > " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "</if>" +
            "<if test = \"user_status_id!=null\" > " +
            "AND u.user_status_id = #{user_status_id} " +
            "</if>" +
            "ORDER BY u.user_id ASC " +
            "OFFSET ${offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "driverResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "userStatusName", column = "user_status_name")})
    List<Driver> findDriversByUserIdAndFullNameAndPhoneNumberAndUserStatus(@Param("user_id") String userID,
                                                                           @Param("full_name") String name,
                                                                           @Param("phone_number") String phoneNumber,
                                                                           @Param("user_status_id") Long userStatusId,
                                                                           @Param("offset") int offset);

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
            "<if test = \"user_id!=null\" > " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "</if>" +
            "<if test = \"full_name!=null\" > " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "</if>" +
            "<if test = \"phone_number!=null\" > " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "</if>" +
            "<if test = \"user_status_id!=null\" > " +
            "AND u.user_status_id = #{user_status_id} " +
            "</if>" +
            "</script>"})
    int findTotalDriversWhenFilter(@Param("user_id") String userID,
                                   @Param("full_name") String name,
                                   @Param("phone_number") String phoneNumber,
                                   @Param("user_status_id") Long userStatusId);
}
