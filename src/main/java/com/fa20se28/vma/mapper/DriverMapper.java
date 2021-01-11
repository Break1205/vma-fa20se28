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
    @Select("SELECT " +
            "drivers.user_id, " +
            "drivers.user_status, " +
            "drivers.full_name, " +
            "drivers.phone_number, " +
            "drivers.gender, " +
            "drivers.date_of_birth, " +
            "drivers.address, " +
            "drivers.image_link, " +
            "drivers.base_salary, " +
            "ISNULL(vehicles.vehicle_id,'') vehicle_id  " +
            "FROM  " +
            "( " +
            "SELECT  " +
            "u.user_id, " +
            "full_name, " +
            "phone_number, " +
            "user_status, " +
            "gender,  " +
            "date_of_birth,  " +
            "address,  " +
            "image_link,  " +
            "base_salary " +
            "FROM  " +
            "[user] u  " +
            "JOIN user_roles ur  " +
            "ON ur.user_id = u.user_id  " +
            "WHERE ur.role_id = 3 " +
            "AND u.user_id = #{user_id}  " +
            ") drivers  " +
            "LEFT JOIN " +
            "( " +
            "SELECT  " +
            "v.vehicle_id,  " +
            "iv.driver_id " +
            "FROM vehicle v " +
            "JOIN issued_vehicle iv  " +
            "ON v.vehicle_id = iv.vehicle_id " +
            "WHERE v.vehicle_status IN ('AVAILABLE','ON_ROUTE','NEED_REPAIR','REPAIRING') " +
            "AND iv.returned_date IS NULL  " +
            ") vehicles  " +
            "ON drivers.user_id = vehicles.driver_id")
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
            "SELECT  " +
            "drivers.user_id,  " +
            "drivers.full_name,  " +
            "drivers.phone_number,  " +
            "drivers.user_status,  " +
            "ISNULL(vehicles.vehicle_id,'') vehicle_id " +
            "FROM " +
            "(  " +
            "SELECT " +
            "u.user_id,  " +
            "full_name,  " +
            "phone_number,  " +
            "user_status " +
            "FROM " +
            "[user] u " +
            "JOIN user_roles ur " +
            "ON ur.user_id = u.user_id " +
            "WHERE ur.role_id = 3  " +
            "<if test = \"DriverPageReq.userId!=null\" >   " +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%'   " +
            "</if>  " +
            "<if test = \"DriverPageReq.fullName!=null\" >   " +
            "AND u.full_name LIKE N'%${DriverPageReq.fullName}%'   " +
            "</if>  " +
            "<if test = \"DriverPageReq.phoneNumber!=null\" >   " +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%'   " +
            "</if>  " +
            "<if test = \"DriverPageReq.userStatus!=null\" >   " +
            "AND u.user_status = #{DriverPageReq.userStatus}   " +
            "</if>  " +
            ") drivers " +
            "LEFT JOIN  " +
            "(  " +
            "SELECT " +
            "v.vehicle_id, " +
            "iv.driver_id  " +
            "FROM vehicle v  " +
            "JOIN issued_vehicle iv " +
            "ON v.vehicle_id = iv.vehicle_id  " +
            "WHERE v.vehicle_status IN ('AVAILABLE','ON_ROUTE','NEED_REPAIR','REPAIRING') " +
            "AND iv.returned_date IS NULL " +
            ") vehicles " +
            "ON drivers.user_id = vehicles.driver_id " +
            "ORDER BY drivers.user_id ASC  " +
            "OFFSET #{DriverPageReq.page} ROWS  " +
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
            "FROM (" +
            "SELECT " +
            "drivers.user_id, " +
            "drivers.full_name, " +
            "drivers.phone_number, " +
            "drivers.user_status, " +
            "ISNULL(vehicles.vehicle_id,'') vehicle_id  " +
            "FROM  " +
            "( " +
            "SELECT  " +
            "u.user_id, " +
            "full_name, " +
            "phone_number, " +
            "user_status  " +
            "FROM  " +
            "[user] u  " +
            "JOIN user_roles ur  " +
            "ON ur.user_id = u.user_id  " +
            "WHERE ur.role_id = 3 " +
            "<if test = \"DriverPageReq.userId!=null\" >  " +
            "AND u.user_id LIKE '%${DriverPageReq.userId}%'  " +
            "</if> " +
            "<if test = \"DriverPageReq.fullName!=null\" >  " +
            "AND u.full_name LIKE N'%${DriverPageReq.fullName}%'  " +
            "</if> " +
            "<if test = \"DriverPageReq.phoneNumber!=null\" >  " +
            "AND u.phone_number LIKE '%${DriverPageReq.phoneNumber}%'  " +
            "</if> " +
            "<if test = \"DriverPageReq.userStatus!=null\" >  " +
            "AND u.user_status = #{DriverPageReq.userStatus}  " +
            "</if> " +
            ") drivers  " +
            "LEFT JOIN " +
            "( " +
            "SELECT  " +
            "v.vehicle_id,  " +
            "iv.driver_id " +
            "FROM vehicle v " +
            "JOIN issued_vehicle iv  " +
            "ON v.vehicle_id = iv.vehicle_id " +
            "WHERE v.vehicle_status IN ('AVAILABLE','ON_ROUTE','NEED_REPAIR','REPAIRING') " +
            "AND iv.returned_date IS NULL  " +
            ") vehicles  " +
            "ON drivers.user_id = vehicles.driver_id ) c " +
            "</script>"})
    int findTotalDriversWhenFilter(@Param("DriverPageReq") DriverPageReq driverPageReq);

    @Select({"<script>" +
            "SELECT  " +
            "u.user_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "u.image_link,  " +
            "iv.vehicle_id " +
            "FROM [user] u " +
            "JOIN ( " +
            "SELECT  " +
            "v.vehicle_id,  " +
            "iv.driver_id  " +
            "FROM vehicle v " +
            "JOIN issued_vehicle iv  " +
            "ON v.vehicle_id = iv.vehicle_id  " +
            "JOIN owner_vehicles ov  " +
            "ON v.vehicle_id = ov.vehicle_id  " +
            "JOIN [user] u  " +
            "ON ov.user_id = u.user_id " +
            "WHERE ov.user_id = #{ownerId}  " +
            "AND v.vehicle_status IN ('AVAILABLE','ON_ROUTE','NEED_REPAIR','REPAIRING') " +
            "AND iv.returned_date IS NULL ) iv " +
            "ON u.user_id = iv.driver_id  " +
            "WHERE 1=1  " +
            "<if test = \"IssuedDriversPageReq.fullName!=null\" > " +
            "AND u.full_name LIKE N'%${IssuedDriversPageReq.fullName}%'  " +
            "</if>  " +
            "<if test = \"IssuedDriversPageReq.phoneNumber!=null\" >  " +
            "AND u.phone_number LIKE '%${IssuedDriversPageReq.phoneNumber}%'  " +
            "</if>  " +
            "<if test = \"IssuedDriversPageReq.vehicleId!=null\" >  " +
            "AND iv.vehicle_id = #{IssuedDriversPageReq.vehicleId}  " +
            "</if>  " +
            "ORDER BY u.user_id ASC " +
            "OFFSET ${IssuedDriversPageReq.page} ROWS " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "issuedDriverResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "vehicleId", column = "vehicle_id")
    })
    List<IssuedDriversRes> findIssuedDrivers(@Param("ownerId") String ownerId,
                                             @Param("IssuedDriversPageReq") IssuedDriversPageReq issuedDriversPageReq);


    @Select({"<script>" +
            "SELECT " +
            "COUNT(u.user_id) " +
            "FROM [user] u " +
            "JOIN ( " +
            "SELECT  " +
            "v.vehicle_id,  " +
            "iv.driver_id  " +
            "FROM vehicle v  " +
            "JOIN issued_vehicle iv  " +
            "ON v.vehicle_id = iv.vehicle_id  " +
            "JOIN owner_vehicles ov  " +
            "ON v.vehicle_id = ov.vehicle_id  " +
            "JOIN [user] u  " +
            "ON ov.user_id = u.user_id  " +
            "WHERE ov.user_id = #{ownerId}  " +
            "AND v.vehicle_status IN ('AVAILABLE','ON_ROUTE','NEED_REPAIR','REPAIRING') " +
            "AND iv.returned_date IS NULL ) iv " +
            "ON u.user_id = iv.driver_id  " +
            "WHERE 1=1  " +
            "<if test = \"IssuedDriversPageReq.fullName!=null\" > " +
            "AND u.full_name LIKE N'%${IssuedDriversPageReq.fullName}%'  " +
            "</if>  " +
            "<if test = \"IssuedDriversPageReq.phoneNumber!=null\" >  " +
            "AND u.phone_number LIKE '%${IssuedDriversPageReq.phoneNumber}%'  " +
            "</if>  " +
            "<if test = \"IssuedDriversPageReq.vehicleId!=null\" >  " +
            "AND iv.vehicle_id = #{IssuedDriversPageReq.vehicleId}  " +
            "</if>  " +
            "</script>"})
    int findTotalIssuedDrivers(@Param("ownerId") String ownerId,
                               @Param("IssuedDriversPageReq") IssuedDriversPageReq issuedDriversPageReq);
}
