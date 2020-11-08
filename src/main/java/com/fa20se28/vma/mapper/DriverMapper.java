package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.IssuedDriversPageReq;
import com.fa20se28.vma.response.DriverRes;
import com.fa20se28.vma.response.IssuedDriversRes;
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
            "ISNULL(iv.vehicle_id,'') vehicle_id " +
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
            "SELECT \n" +
            "drivers.user_id, \n" +
            "drivers.full_name, \n" +
            "drivers.phone_number, \n" +
            "drivers.user_status, \n" +
            "ISNULL(vehicles.vehicle_id,'') vehicle_id\n" +
            "FROM\n" +
            "( \n" +
            "SELECT\n" +
            "u.user_id, \n" +
            "full_name, \n" +
            "phone_number, \n" +
            "user_status\n" +
            "FROM\n" +
            "[user] u\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = 3 \n" +
            "<if test = \"DriverPageReq.userId!=null\" >  \n" +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%'  \n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.fullName!=null\" >  \n" +
            "AND u.full_name LIKE N'%${DriverPageReq.fullName}%'  \n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.phoneNumber!=null\" >  \n" +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%'  \n" +
            "</if> \n" +
            "<if test = \"DriverPageReq.userStatus!=null\" >  \n" +
            "AND u.user_status = #{DriverPageReq.userStatus}  \n" +
            "</if> \n" +
            ") drivers\n" +
            "LEFT JOIN \n" +
            "( \n" +
            "SELECT\n" +
            "v.vehicle_id,\n" +
            "iv.driver_id \n" +
            "FROM vehicle v \n" +
            "JOIN issued_vehicle iv\n" +
            "ON v.vehicle_id = iv.vehicle_id \n" +
            "WHERE v.vehicle_status IN ('AVAILABLE','ON_ROUTE')\n" +
            "AND iv.returned_date IS NULL\n" +
            ") vehicles\n" +
            "ON drivers.user_id = vehicles.driver_id " +
            "ORDER BY drivers.user_id ASC \n" +
            "OFFSET #{DriverPageReq.page} ROWS \n" +
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
            "SELECT COUNT(c.user_id) " +
            "FROM ("+
            "SELECT\n" +
            "drivers.user_id,\n" +
            "drivers.full_name,\n" +
            "drivers.phone_number,\n" +
            "drivers.user_status,\n" +
            "ISNULL(vehicles.vehicle_id,'') vehicle_id \n" +
            "FROM \n" +
            "(\n" +
            "SELECT \n" +
            "u.user_id,\n" +
            "full_name,\n" +
            "phone_number,\n" +
            "user_status \n" +
            "FROM \n" +
            "[user] u \n" +
            "JOIN user_roles ur \n" +
            "ON ur.user_id = u.user_id \n" +
            "WHERE ur.role_id = 3\n" +
            "<if test = \"DriverPageReq.userId!=null\" > \n" +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%' \n" +
            "</if>\n" +
            "<if test = \"DriverPageReq.fullName!=null\" > \n" +
            "AND u.full_name LIKE N'%${DriverPageReq.fullName}%' \n" +
            "</if>\n" +
            "<if test = \"DriverPageReq.phoneNumber!=null\" > \n" +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%' \n" +
            "</if>\n" +
            "<if test = \"DriverPageReq.userStatus!=null\" > \n" +
            "AND u.user_status = #{DriverPageReq.userStatus} \n" +
            "</if>\n" +
            ") drivers \n" +
            "LEFT JOIN\n" +
            "(\n" +
            "SELECT \n" +
            "v.vehicle_id, \n" +
            "iv.driver_id\n" +
            "FROM vehicle v\n" +
            "JOIN issued_vehicle iv \n" +
            "ON v.vehicle_id = iv.vehicle_id\n" +
            "WHERE v.vehicle_status IN ('AVAILABLE','ON_ROUTE') \n" +
            "AND iv.returned_date IS NULL \n" +
            ") vehicles \n" +
            "ON drivers.user_id = vehicles.driver_id ) c\n" +
            "</script>"})
    int findTotalDriversWhenFilter(@Param("DriverPageReq") DriverPageReq driverPageReq);

    @Select({"<script>" +
            "SELECT \n" +
            "u.user_id, \n" +
            "u.full_name, \n" +
            "u.phone_number, \n" +
            "iv.vehicle_id \n" +
            "FROM [user] u \n" +
            "JOIN issued_vehicle iv \n" +
            "ON u.user_id = iv.driver_id \n" +
            "JOIN vehicle v \n" +
            "ON iv.vehicle_id = v.vehicle_id\n" +
            "WHERE v.owner_id = '${ownerId}'\n" +
            "AND v.vehicle_status = 'ON_ROUTE'" +
            "<if test = \"IssuedDriversPageReq.fullName!=null\" > \n" +
            "AND u.full_name LIKE N'%${IssuedDriversPageReq.fullName}%' \n" +
            "</if>  \n" +
            "<if test = \"IssuedDriversPageReq.phoneNumber!=null\" > \n" +
            "AND u.phone_number LIKE '%${IssuedDriversPageReq.phoneNumber}%' \n" +
            "</if>  \n" +
            "<if test = \"IssuedDriversPageReq.vehicleId!=null\" > \n" +
            "AND iv.vehicle_id = #{IssuedDriversPageReq.vehicleId} \n" +
            "</if>  \n" +
            "ORDER BY u.user_id ASC \n" +
            "OFFSET ${IssuedDriversPageReq.page} ROWS \n" +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "issuedDriverResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "vehicleId", column = "vehicle_id")
    })
    List<IssuedDriversRes> findIssuedDrivers(@Param("ownerId") String ownerId,
                                             @Param("IssuedDriversPageReq") IssuedDriversPageReq issuedDriversPageReq);


    @Select({"<script>" +
            "SELECT COUNT(id.user_id) \n" +
            "FROM (\n" +
            "SELECT\n" +
            "u.user_id,\n" +
            "u.full_name,\n" +
            "u.phone_number,\n" +
            "iv.vehicle_id\n" +
            "FROM [user] u\n" +
            "JOIN issued_vehicle iv\n" +
            "ON u.user_id = iv.driver_id\n" +
            "JOIN vehicle v\n" +
            "ON iv.vehicle_id = v.vehicle_id \n" +
            "WHERE v.owner_id = '${ownerId}' \n" +
            "AND v.vehicle_status = 'ON_ROUTE' \n" +
            "<if test = \"IssuedDriversPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${IssuedDriversPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"IssuedDriversPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${IssuedDriversPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "<if test = \"IssuedDriversPageReq.vehicleId!=null\" >\n" +
            "AND iv.vehicle_id = #{IssuedDriversPageReq.vehicleId}\n" +
            "</if> " +
            ") id" +
            "</script>"})
    int findTotalIssuedDrivers(@Param("ownerId") String ownerId,
                               @Param("IssuedDriversPageReq") IssuedDriversPageReq issuedDriversPageReq);
}
