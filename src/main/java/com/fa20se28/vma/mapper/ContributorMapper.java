package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.ContributorDetail;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.response.ContributorRes;
import com.fa20se28.vma.response.UserRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ContributorMapper {
    @Select({"<script>" +
            "SELECT\n" +
            "u.user_id,\n" +
            "u.user_status,\n" +
            "u.full_name,\n" +
            "u.phone_number,\n" +
            "u.gender,\n" +
            "u.date_of_birth,\n" +
            "u.address,\n" +
            "u.image_link,\n" +
            "u.base_salary,\n" +
            "COUNT(v.vehicle_id) as total_vehicles\n" +
            "FROM vehicle v\n" +
            "RIGHT JOIN [user] u\n" +
            "ON u.user_id = v.owner_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 2\n" +
            "AND u.user_id = '${user_id}'\n" +
            "GROUP BY \n" +
            "u.user_id,\n" +
            "u.user_status,\n" +
            "u.full_name,\n" +
            "u.phone_number, \n" +
            "u.gender,\n" +
            "u.date_of_birth,\n" +
            "u.address,\n" +
            "u.image_link,\n" +
            "u.base_salary " +
            "</script>"})
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
            "SELECT\n" +
            "u.user_id,\n" +
            "u.full_name,\n" +
            "u.phone_number,\n" +
            "u.user_status,\n" +
            "COUNT(v.vehicle_id) as total_vehicles\n" +
            "FROM vehicle v\n" +
            "RIGHT JOIN [user] u\n" +
            "ON u.user_id = v.owner_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 2\n" +
            "<if test = \"ContributorPageReq.userStatus!=null\" >\n" +
            "AND u.user_status = #{ContributorPageReq.userStatus}\n" +
            "</if> \n" +
            "<if test = \"ContributorPageReq.userId!=null\" >\n" +
            "AND u.user_id LIKE '%${ContributorPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"ContributorPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${ContributorPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"ContributorPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${ContributorPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "GROUP BY u.user_id,u.full_name,u.phone_number,u.user_status\n" +
            "HAVING COUNT(v.vehicle_id) <![CDATA[>=]]> ${ContributorPageReq.min}\n" +
            "<if test = \"ContributorPageReq.max!=null\" >\n" +
            "AND COUNT(v.vehicle_id) <![CDATA[<=]]> ${ContributorPageReq.max}\n" +
            "</if> \n" +
            "ORDER BY u.user_id ASC\n" +
            "OFFSET ${ContributorPageReq.page} ROWS\n" +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "contributorResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "totalVehicles", column = "total_vehicles"),
            @Result(property = "userStatus", column = "user_status")})
    List<ContributorRes> findContributorsWhenFilter(
            @Param("ContributorPageReq") ContributorPageReq contributorPageReq);

    @Select({"<script>" +
            "SELECT count(tc.user_id)\n" +
            "FROM\n" +
            "(SELECT\n" +
            "u.user_id,\n" +
            "u.full_name,\n" +
            "u.phone_number,\n" +
            "u.user_status,\n" +
            "COUNT(v.vehicle_id) as total_vehicles\n" +
            "FROM vehicle v\n" +
            "RIGHT JOIN [user] u\n" +
            "ON u.user_id = v.owner_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 2\n" +
            "<if test = \"ContributorPageReq.userStatus!=null\" >\n" +
            "AND u.user_status = #{ContributorPageReq.userStatus}\n" +
            "</if> \n" +
            "<if test = \"ContributorPageReq.userId!=null\" >\n" +
            "AND u.user_id LIKE '%${ContributorPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"ContributorPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${ContributorPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"ContributorPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${ContributorPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "GROUP BY u.user_id,u.full_name,u.phone_number,u.user_status\n" +
            "HAVING COUNT(v.vehicle_id) <![CDATA[>=]]> ${ContributorPageReq.min}\n" +
            "<if test = \"ContributorPageReq.max!=null\" >\n" +
            "AND COUNT(v.vehicle_id) <![CDATA[<=]]> ${ContributorPageReq.max}\n" +
            "</if> \n" +
            ") tc " +
            "</script>"})
    int findTotalContributorsWhenFilter(
            @Param("ContributorPageReq") ContributorPageReq contributorPageReq);

    @Select("SELECT Max(con.total_vehicles)\n" +
            "FROM ( \n" +
            "SELECT \n" +
            "count(v.owner_id) as total_vehicles\n" +
            "FROM vehicle v\n" +
            "RIGHT JOIN [user] u\n" +
            "ON u.user_id = v.owner_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 2\n" +
            "AND v.vehicle_status != 'DELETED'\n" +
            "GROUP BY v.owner_id) as con")
    int findTheHighestTotalVehiclesInAllContributors();

    @Select("SELECT Min(con.total_vehicles)\n" +
            "FROM ( \n" +
            "SELECT \n" +
            "count(v.owner_id) as total_vehicles\n" +
            "FROM vehicle v\n" +
            "RIGHT JOIN [user] u\n" +
            "ON u.user_id = v.owner_id\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 2\n" +
            "AND v.vehicle_status != 'DELETED'\n" +
            "GROUP BY v.owner_id) as con")
    int findTheLowestTotalVehiclesInAllContributors();

    @Select({"<script>" +
            "SELECT \n" +
            "u.user_id,  \n" +
            "u.full_name,  \n" +
            "u.phone_number,  \n" +
            "u.user_status \n" +
            "FROM \n" +
            "[user] u \n" +
            "WHERE u.user_id = \n" +
            "(\n" +
            "SELECT driver_id \n" +
            "FROM issued_vehicle iv \n" +
            "JOIN vehicle v \n" +
            "ON iv.vehicle_id = v.vehicle_id \n" +
            "WHERE v.owner_id = '${ownerId}' \n" +
            "AND iv.returned_date IS NULL\n" +
            ")\n" +
            "<if test = \"UserPageReq.fullName!=null\" > \n" +
            "AND u.full_name LIKE N'%${UserPageReq.fullName}%' \n" +
            "</if>  \n" +
            "<if test = \"UserPageReq.phoneNumber!=null\" > \n" +
            "AND u.phone_number LIKE '%${UserPageReq.phoneNumber}%' \n" +
            "</if>  \n" +
            "<if test = \"UserPageReq.userStatus!=null\" > \n" +
            "AND u.user_status = #{UserPageReq.userStatus} \n" +
            "</if>  \n" +
            "ORDER BY u.user_id ASC \n" +
            "OFFSET ${UserPageReq.page} ROWS \n" +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "usersWithOnlyOneRoleResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "userStatus", column = "user_status")
    })
    List<UserRes> findDriversDriveIssuedVehicleOfContributor(@Param("ownerId") String ownerId,
                                                             @Param("UserPageReq") UserPageReq userPageReq);


    @Select({"<script>" +
            "SELECT COUNT(uu.user_id) \n" +
            "FROM (\n" +
            "SELECT \n" +
            "u.user_id, \n" +
            "u.full_name, \n" +
            "u.phone_number, \n" +
            "u.user_status \n" +
            "FROM \n" +
            "[user] u \n" +
            "WHERE u.user_id = \n" +
            "( \n" +
            "SELECT driver_id \n" +
            "FROM issued_vehicle iv \n" +
            "JOIN vehicle v \n" +
            "ON iv.vehicle_id = v.vehicle_id \n" +
            "WHERE v.owner_id = '${ownerId}' \n" +
            "AND iv.returned_date IS NULL \n" +
            ") \n" +
            "<if test = \"UserPageReq.fullName!=null\" > \n" +
            "AND u.full_name LIKE N'%${UserPageReq.fullName}%' \n" +
            "</if> \n" +
            "<if test = \"UserPageReq.phoneNumber!=null\" > \n" +
            "AND u.phone_number LIKE '%${UserPageReq.phoneNumber}%' \n" +
            "</if> \n" +
            "<if test = \"UserPageReq.userStatus!=null\" > \n" +
            "AND u.user_status = #{UserPageReq.userStatus} \n" +
            "</if> \n" +
            ") uu" +
            "</script>"})
    int findTotalDriversDriveIssuedVehicleOfContributor(@Param("ownerId") String ownerId,
                                                        @Param("UserPageReq") UserPageReq userPageReq);
}
