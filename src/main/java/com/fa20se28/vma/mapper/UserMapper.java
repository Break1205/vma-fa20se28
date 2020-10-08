package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.model.UserStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

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

    @Select("SELECT " +
            "u.user_id," +
            "u.full_name," +
            "u.phone_number," +
            "iv.vehicle_id," +
            "us.user_status_name " +
            "FROM " +
            "[user] u " +
            "JOIN issued_vehicle iv " +
            "ON u.user_id= iv.driver_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 3 " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "AND us.user_status_id = ${user_status_id} " +
            "ORDER BY u.user_id ASC " +
            "OFFSET ${offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY")
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

    @Select("SELECT " +
            "u.user_id," +
            "u.full_name," +
            "u.phone_number," +
            "iv.vehicle_id," +
            "us.user_status_name " +
            "FROM " +
            "[user] u " +
            "JOIN issued_vehicle iv " +
            "ON u.user_id= iv.driver_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 3 " +
            "ORDER BY u.user_id ASC " +
            "OFFSET ${offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY")
    @ResultMap("driverResult")
    List<Driver> getDrivers(@Param("offset") int offset);

    @Select("SELECT count(*) as size " +
            "FROM [user] u " +
            "JOIN user_roles ur " +
            "ON u.user_id = ur.user_id " +
            "WHERE ur.role_id = ${role_id} ")
    int findNumberOfUsers(@Param("role_id") Long roleId);

    @Select("SELECT " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM (vehicle v " +
            "INNER JOIN [user] u " +
            "ON u.user_id = v.owner_id) " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "GROUP BY u.user_id,u.full_name,u.phone_number " +
            "HAVING COUNT(v.vehicle_id) = ${total_vehicles}" +
            "ORDER BY u.user_id DESC " +
            "OFFSET ${offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY")
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

    @Select("SELECT " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM (vehicle v " +
            "INNER JOIN [user] u " +
            "ON u.user_id = v.owner_id) " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "GROUP BY u.user_id,u.full_name,u.phone_number " +
            "ORDER BY u.user_id DESC " +
            "OFFSET ${offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY")
    @ResultMap("contributorResult")
    List<Contributor> getContributors(@Param("offset") int offset);
}
