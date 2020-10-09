package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT [user_id]" +
            "      ,[user_status_id]" +
            "      ,[password]" +
            "      ,[full_name]" +
            "      ,[phone_number]" +
            "      ,[gender]" +
            "      ,[date_of_birth]" +
            "      ,[address]" +
            "      ,[image_link]" +
            "      ,[base_salary]" +
            "  FROM [user]" +
            " WHERE [user_id]=${user_id}")
    @Results(id = "userResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userStatus", column = "user_status_id", javaType = UserStatus.class, one = @One(select = "findUserStatusById")),
            @Result(property = "password", column = "password"),
            @Result(property = "fullname", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
            @Result(property = "address", column = "address"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "baseSalary", column = "base_salary")
    })
    User findUserById(@Param(value = "user_id") Long userId);

    @Select("SELECT [user_status_id]" +
            "      ,[user_status_name]" +
            "  FROM [user_status]" +
            "  WHERE [user_status_id] = ${user_status_id}")
    @Results(id = "userStatusResult", value = {
            @Result(property = "userStatusId", column = "user_status_id"),
            @Result(property = "userStatusName", column = "user_status_name")
    })
    UserStatus findUserStatusById(@Param(value = "user_status_id") Long userStatusId);

    @Select("SELECT r.[role_id]" +
            "      ,r.[role_name]" +
            "  FROM [user_roles] ur join [role] r on ur.role_id=r.role_id" +
            "  WHERE [user_id]= ${user_id}")
    @Results(id = "roleResult", value = {
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name")
    })
    List<Role> findRolesByUserId(@Param(value = "user_id") Long userId);

    @Select("Select * from [user]")
    @ResultMap(value = "userResult")
    List<User> findUsers();

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
            "AND u.user_status_id LIKE '%${user_status_id}%' " +
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
            "SELECT " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM (vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id) " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "<if test = \"user_id!=null\" > " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "</if>" +
            "<if test = \"full_name!=null\" > " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "</if>" +
            "<if test = \"phone_number!=null\" > " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "</if>" +
            "GROUP BY u.user_id,u.full_name,u.phone_number " +
            "<if test = \"total_vehicles!=null\" > " +
            "HAVING COUNT(v.vehicle_id) = ${total_vehicles} " +
            "</if>" +
            "ORDER BY u.user_id DESC " +
            "OFFSET ${offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "contributorResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "totalVehicles", column = "total_vehicles")})
    List<Contributor> findContributorsByUserIdAndFullNameAndPhoneNumberAndTotalVehicle(@Param("user_id") String userID,
                                                                                       @Param("full_name") String name,
                                                                                       @Param("phone_number") String phoneNumber,
                                                                                       @Param("total_vehicles") Long totalVehicles,
                                                                                       @Param("offset") int offset);

    @Select("SELECT count(DISTINCT u.user_id)" +
            "FROM [user] u join user_roles ur on u.user_id = ur.user_id where role_id = ${role_id}")
    int findTotalUserByRoles(@Param("role_id") int roleId);

    @Select({"<script>" +
            "SELECT " +
            "count(DISTINCT u.user_id) " +
            "FROM (vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id) " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "<if test = \"user_id!=null\" > " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "</if>" +
            "<if test = \"full_name!=null\" > " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "</if>" +
            "<if test = \"phone_number!=null\" > " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "</if>" +
            "<if test = \"total_vehicles!=null\" > " +
            "HAVING COUNT(v.vehicle_id) = ${total_vehicles} " +
            "</if> " +
            "</script>"})
    int findTotalContributorsWhenFilter(@Param("user_id") String userID,
                                        @Param("full_name") String name,
                                        @Param("phone_number") String phoneNumber,
                                        @Param("total_vehicles") Long totalVehicles);

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
            "AND u.user_status_id LIKE '%${user_status_id}%' " +
            "</if>" +
            "</script>"})
    int findTotalDriversWhenFilter(@Param("user_id") String userID,
                                   @Param("full_name") String name,
                                   @Param("phone_number") String phoneNumber,
                                   @Param("user_status_id") Long userStatusId);
}
