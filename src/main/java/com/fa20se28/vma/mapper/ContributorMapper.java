package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.ContributorDetail;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.response.ContributorRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ContributorMapper {
    @Select("SELECT " +
            "u.user_id, " +
            "u.user_status, " +
            "u.full_name, " +
            "u.phone_number, " +
            "u.gender, " +
            "u.date_of_birth, " +
            "u.address, " +
            "u.image_link, " +
            "u.base_salary, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM vehicle v " +
            "JOIN owner_vehicles ov  " +
            "ON v.vehicle_id = ov.vehicle_id  " +
            "RIGHT JOIN [user] u " +
            "ON u.user_id = ov.user_id " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 2 " +
            "AND ov.end_date IS NULL " +
            "AND u.user_id = #{user_id} " +
            "GROUP BY  " +
            "u.user_id, " +
            "u.user_status, " +
            "u.full_name, " +
            "u.phone_number,  " +
            "u.gender, " +
            "u.date_of_birth, " +
            "u.address, " +
            "u.image_link, " +
            "u.base_salary ")
    @Results(id = "contributorDetailResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "totalVehicles", column = "total_vehicles"),
            @Result(property = "userStatus", column = "user_status"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
            @Result(property = "address", column = "address"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "baseSalary", column = "base_salary"),
    })
    Optional<ContributorDetail> findContributorById(@Param("user_id") String userId);

    @Select({"<script>" +
            "SELECT " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "u.user_status, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM vehicle v  " +
            "JOIN owner_vehicles ov  " +
            "ON v.vehicle_id = ov.vehicle_id " +
            "RIGHT JOIN [user] u  " +
            "ON u.user_id = ov.user_id " +
            "JOIN user_roles ur  " +
            "ON ur.user_id = u.user_id  " +
            "WHERE ur.role_id = 2 " +
            "AND ov.end_date IS NULL " +
            "<if test = \"ContributorPageReq.userStatus!=null\" > " +
            "AND u.user_status = #{ContributorPageReq.userStatus} " +
            "</if>  " +
            "<if test = \"ContributorPageReq.userId!=null\" > " +
            "AND u.user_id LIKE '%${ContributorPageReq.userId}%' " +
            "</if>  " +
            "<if test = \"ContributorPageReq.fullName!=null\" > " +
            "AND u.full_name LIKE N'%${ContributorPageReq.fullName}%' " +
            "</if>  " +
            "<if test = \"ContributorPageReq.phoneNumber!=null\" > " +
            "AND u.phone_number LIKE '%${ContributorPageReq.phoneNumber}%' " +
            "</if>  " +
            "GROUP BY u.user_id,u.full_name,u.phone_number,u.user_status " +
            "HAVING COUNT(v.vehicle_id) &gt;= ${ContributorPageReq.min} " +
            "<if test = \"ContributorPageReq.max!=null\" > " +
            "AND COUNT(v.vehicle_id) &lt;= ${ContributorPageReq.max} " +
            "</if>  " +
            "ORDER BY u.user_id ASC " +
            "OFFSET ${ContributorPageReq.page} ROWS " +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "contributorResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "totalVehicles", column = "total_vehicles"),
            @Result(property = "userStatus", column = "user_status")})
    List<ContributorRes> findContributors(
            @Param("ContributorPageReq") ContributorPageReq contributorPageReq);

    @Select({"<script>" +
            "SELECT count(tc.user_id) " +
            "FROM " +
            "(SELECT " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "u.user_status, " +
            "COUNT(v.vehicle_id) as total_vehicles " +
            "FROM vehicle v  " +
            "JOIN owner_vehicles ov  " +
            "ON v.vehicle_id = ov.vehicle_id " +
            "RIGHT JOIN [user] u  " +
            "ON u.user_id = ov.user_id " +
            "JOIN user_roles ur  " +
            "ON ur.user_id = u.user_id  " +
            "WHERE ur.role_id = 2 " +
            "AND ov.end_date IS NULL " +
            "<if test = \"ContributorPageReq.userStatus!=null\" > " +
            "AND u.user_status = #{ContributorPageReq.userStatus} " +
            "</if>  " +
            "<if test = \"ContributorPageReq.userId!=null\" > " +
            "AND u.user_id LIKE '%${ContributorPageReq.userId}%' " +
            "</if>  " +
            "<if test = \"ContributorPageReq.fullName!=null\" > " +
            "AND u.full_name LIKE N'%${ContributorPageReq.fullName}%' " +
            "</if>  " +
            "<if test = \"ContributorPageReq.phoneNumber!=null\" > " +
            "AND u.phone_number LIKE '%${ContributorPageReq.phoneNumber}%' " +
            "</if>  " +
            "GROUP BY u.user_id,u.full_name,u.phone_number,u.user_status " +
            "HAVING COUNT(v.vehicle_id) &gt;= ${ContributorPageReq.min} " +
            "<if test = \"ContributorPageReq.max!=null\" > " +
            "AND COUNT(v.vehicle_id) &lt;= ${ContributorPageReq.max} " +
            "</if>  " +
            ") tc " +
            "</script>"})
    int findTotalContributors(
            @Param("ContributorPageReq") ContributorPageReq contributorPageReq);
}
