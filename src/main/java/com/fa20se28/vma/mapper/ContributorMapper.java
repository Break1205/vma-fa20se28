package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.ContributorDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContributorMapper {
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
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "WHERE ur.role_id = 2 " +
            "AND v.is_deleted = 0 " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "GROUP BY  " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "us.user_status_name, " +
            "u.gender, " +
            "u.date_of_birth, " +
            "u.address, " +
            "u.image_link, " +
            "u.base_salary" +
            "</script>"})
    @Results(id = "contributorDetailResult",value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "totalVehicles", column = "total_vehicles"),
            @Result(property = "userStatusName", column = "user_status_name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
            @Result(property = "address", column = "address"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "baseSalary", column = "base_salary"),
    })
    ContributorDetail findContributorById(@Param("user_id") String userId);

    @Select({"<script>" +
            "SELECT " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "us.user_status_name, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "JOIN user_status us " +
            "ON u.user_status_id = us.user_status_id " +
            "WHERE ur.role_id = 2 " +
            "AND v.is_deleted = 0 " +
            "<if test = \"user_id!=null\" > " +
            "AND u.user_id LIKE '%${user_id}%' " +
            "</if>" +
            "<if test = \"full_name!=null\" > " +
            "AND u.full_name LIKE '%${full_name}%' " +
            "</if>" +
            "<if test = \"phone_number!=null\" > " +
            "AND u.phone_number LIKE '%${phone_number}%' " +
            "</if>" +
            "GROUP BY u.user_id,u.full_name,u.phone_number,us.user_status_name " +
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
            @Result(property = "totalVehicles", column = "total_vehicles"),
            @Result(property = "userStatusName", column = "user_status_name")})
    List<Contributor> findContributorsByUserIdAndFullNameAndPhoneNumberAndTotalVehicle(@Param("user_id") String userID,
                                                                                       @Param("full_name") String name,
                                                                                       @Param("phone_number") String phoneNumber,
                                                                                       @Param("total_vehicles") Long totalVehicles,
                                                                                       @Param("offset") int offset);

    @Select({"<script>" +
            "SELECT " +
            "count(DISTINCT u.user_id) " +
            "FROM vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "AND v.is_deleted = 0 " +
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

    @Select("SELECT Max(con.total_vehicles) " +
            "FROM (" +
            "SELECT  " +
            "count(v.owner_id) as total_vehicles " +
            "FROM vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "AND v.is_deleted = 0" +
            "GROUP BY v.owner_id) as con")
    int findTheHighestTotalVehiclesInAllContributors();

    @Select("SELECT Min(con.total_vehicles) " +
            "FROM (" +
            "SELECT  " +
            "count(v.owner_id) as total_vehicles " +
            "FROM vehicle v " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "AND v.is_deleted = 0" +
            "GROUP BY v.owner_id) as con")
    int findTheLowestTotalVehiclesInAllContributors();
}
