package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorIncome;
import com.fa20se28.vma.model.ContributorIncomesDetail;
import com.fa20se28.vma.model.DriverIncomes;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.RevenueExpense;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.VehicleReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Select({"<script>" +
            "SELECT  " +
            "iv.vehicle_id,  " +
            "c.contract_id,  " +
            "cd.departure_location,  " +
            "cd.departure_time,  " +
            "cd.destination_location,  " +
            "cd.destination_time,  " +
            "cv.contract_vehicle_status  " +
            "FROM issued_vehicle iv  " +
            "JOIN contract_vehicles cv  " +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id  " +
            "JOIN contract_detail cd  " +
            "ON cv.contract_detail_id = cd.contract_detail_id  " +
            "JOIN contract c " +
            "ON c.contract_id = cd.contract_id " +
            "WHERE 1 = 1  " +
            "<if test = \"status!=null\" > " +
            "AND cv.contract_vehicle_status  = #{status} " +
            "</if>  " +
            "AND cd.departure_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' " +
            "ORDER BY cd.departure_time ASC" +
            "</script>"})
    @Results(id = "scheduleResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status"),
    })
    List<Schedule> getListSchedule(@Param("firstDayOfMonth") String firstDayOfMonth,
                                   @Param("lastDayOfMonth") String lastDayOfMonth,
                                   @Param("status") String status);

    @Select({"<script>" +
            "SELECT " +
            "v.vehicle_id," +
            "vt.vehicle_type_name, " +
            "b.brand_name, " +
            "u.user_id, " +
            "u.full_name " +
            "FROM vehicle v " +
            "JOIN vehicle_type vt " +
            "ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN brand b " +
            "ON v.brand_id = b.brand_id " +
            "JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "WHERE 1 = 1 " +
            "<if test = \"status!=null\" >" +
            "AND v.vehicle_status = #{status} " +
            "</if> " +
            "ORDER BY u.user_id" +
            "</script>"})
    @Results(id = "vehicleReportResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType", column = "vehicle_type_name"),
            @Result(property = "brand", column = "brand_name"),
            @Result(property = "ownerId", column = "user_id"),
            @Result(property = "ownerName", column = "full_name")
    })
    List<VehicleReport> getVehiclesForReport(@Param("status") String status);

    @Select({"<script>" +
            "SELECT " +
            "COUNT(v.vehicle_id) " +
            "FROM vehicle v  " +
            "JOIN vehicle_type vt " +
            "ON v.vehicle_type_id = vt.vehicle_type_id " +
            "JOIN brand b " +
            "ON v.brand_id = b.brand_id " +
            "JOIN [user] u " +
            "ON u.user_id = v.owner_id " +
            "WHERE 1 = 1 " +
            "<if test = \"status!=null\" >" +
            "AND v.vehicle_status = #{status} " +
            "</if> " +
            "</script>"})
    int getTotalVehicleForReport(@Param("status") String status);

    @Select({"<script>" +
            "SELECT  " +
            "vehicle_id,  " +
            "maintenance_type,  " +
            "start_date,  " +
            "end_date,  " +
            "cost, " +
            "description " +
            "FROM maintenance  " +
            "WHERE 1 = 1 " +
            "<if test = \"vehicleId!=null\" >  " +
            "AND vehicle_id = #{vehicleId}  " +
            "</if>   " +
            "AND start_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' " +
            "</script>"})
    @Results(id = "maintenanceReportResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "maintenanceType", column = "maintenance_type"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "cost", column = "cost"),
            @Result(property = "description", column = "description"),
    })
    List<MaintenanceReport> getMaintenanceByVehicleIdForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                               @Param("lastDayOfMonth") String lastDayOfMonth,
                                                               @Param("vehicleId") String vehicleId);

    @Select({"<script>" +
            "SELECT " +
            "c.contract_id, " +
            "c.total_price contract_value,  " +
            "c.is_round_trip,  " +
            "cd.departure_time,  " +
            "cd.departure_location, " +
            "cd.destination_location, " +
            "cu.customer_id, " +
            "cu.customer_name, " +
            "cu.phone_number,  " +
            "cu.email,  " +
            "cu.fax,  " +
            "cu.account_number,  " +
            "cu.tax_code  " +
            "FROM contract c  " +
            "JOIN customer cu  " +
            "ON c.contract_owner_id = cu.customer_id " +
            "JOIN ( " +
            "SELECT  " +
            "TOP 1 " +
            "contract_id,  " +
            "departure_time,  " +
            "departure_location,  " +
            "destination_location  " +
            "FROM contract_detail  " +
            "ORDER BY create_date) cd  " +
            "ON c.contract_id = cd.contract_id " +
            "WHERE 1 =1  " +
            "<if test = \"status!=null\" >  " +
            "AND c.contract_status = #{status}  " +
            "</if> " +
            "AND cd.departure_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' " +
            "</script>"})
    @Results(id = "contractReportResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractValue", column = "contract_value"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "email", column = "email"),
            @Result(property = "fax", column = "fax"),
            @Result(property = "accountNumber", column = "account_number"),
            @Result(property = "taxCode", column = "tax_code"),
    })
    List<ContractReport> getContractsReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                            @Param("lastDayOfMonth") String lastDayOfMonth,
                                            @Param("status") String status);

    @Select("SELECT  " +
            "cd.destination_time date,  " +
            "'CONTRACT_REVENUE' type, " +
            "c.total_price/c.actual_vehicle_count value,  " +
            "CONVERT(varchar,c.contract_id) contract_id, " +
            "cu.customer_id " +
            "FROM issued_vehicle iv " +
            "JOIN contract_vehicles cv  " +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id  " +
            "JOIN contract_detail cd " +
            "ON cv.contract_detail_id = cd.contract_detail_id " +
            "JOIN contract c  " +
            "ON c.contract_id = cd.contract_id  " +
            "JOIN customer cu " +
            "ON cu.customer_id = c.contract_owner_id " +
            "WHERE iv.vehicle_id = #{vehicleId} " +
            "AND c.contract_status = 'FINISHED'  " +
            "AND cd.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}'  " +
            "UNION  " +
            "SELECT  " +
            "start_date date,  " +
            "maintenance_type type,  " +
            "cost value, " +
            "'N/A' contract_id, " +
            "'N/A' customer_id " +
            "FROM maintenance " +
            "WHERE vehicle_id = #{vehicleId} " +
            "AND is_deleted = 0 " +
            "AND start_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @Results(id = "revenueExpenseResult", value = {
            @Result(property = "date", column = "date"),
            @Result(property = "type", column = "type"),
            @Result(property = "value", column = "value"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "customerId", column = "customer_id")
    })
    List<RevenueExpense> getVehicleRevenueExpenseForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                           @Param("lastDayOfMonth") String lastDayOfMonth,
                                                           @Param("vehicleId") String vehicleId);

    @Select("SELECT " +
            "c.duration_to date,  " +
            "'CONTRACT_REVENUE' type, " +
            "(c.total_price)-(c.total_price*25/100)-(c.total_price*10/100) value,  " +
            "CONVERT(varchar,c.contract_id) contract_id, " +
            "cu.customer_id " +
            "FROM contract c  " +
            "JOIN customer cu " +
            "ON cu.customer_id = c.contract_owner_id " +
            "WHERE c.contract_status = 'FINISHED'  " +
            "AND c.duration_to between '${firstDayOfMonth}' AND '${lastDayOfMonth}'  " +
            "UNION  " +
            "SELECT  " +
            "start_date date,  " +
            "maintenance_type type,  " +
            "cost value, " +
            "'N/A' contract_id, " +
            "'N/A' customer_id " +
            "FROM maintenance " +
            "WHERE is_deleted = 0 " +
            "AND start_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @ResultMap("revenueExpenseResult")
    List<RevenueExpense> getCompanyRevenueExpenseForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                           @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select({"<script>" +
            "SELECT  " +
            "vv.vehicle_id,  " +
            "vv.value,  " +
            "vv.start_date,  " +
            "vv.end_date,  " +
            "v.owner_id  " +
            "FROM  " +
            "vehicle_value vv " +
            "LEFT JOIN vehicle v " +
            "ON vv.vehicle_id = v.vehicle_id " +
            "WHERE vv.is_deleted = 0  " +
            "AND NOT (vv.start_date &gt; '${lastDayOfMonth}' OR vv.end_date &lt; '${firstDayOfMonth}')  " +
            "<if test = \"ownerId!=null\" > " +
            "AND v.owner_id = #{ownerId}  " +
            "</if> " +
            "</script>"})
    @Results(id = "contributorIncomesResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "value", column = "value"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "ownerId", column = "owner_id")
    })
    List<ContributorIncome> getContributorIncomesForReport(@Param("ownerId") String ownerId,
                                                           @Param("firstDayOfMonth") String firstDayOfMonth,
                                                           @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select({"<script>" +
            "SELECT  " +
            "v.owner_id, " +
            "v.vehicle_id, " +
            "cd.destination_time date, " +
            "c.total_price/c.actual_vehicle_count*25/100 value, " +
            "CONVERT(varchar,c.contract_id) contract_id, " +
            "cd.contract_detail_id  " +
            "FROM vehicle v  " +
            "JOIN issued_vehicle iv  " +
            "ON v.vehicle_id = iv.vehicle_id " +
            "JOIN contract_vehicles cv " +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id " +
            "JOIN contract_detail cd " +
            "ON cv.contract_detail_id = cd.contract_detail_id " +
            "JOIN contract c " +
            "ON c.contract_id = cd.contract_id " +
            "WHERE c.contract_status = 'FINISHED'" +
            "AND cd.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' " +
            "<if test = \"ownerId!=null\" > " +
            "AND v.owner_id = #{ownerId}  " +
            "</if>  " +
            "</script>"})
    @Results(id = "contributorIncomesDetailResult", value = {
            @Result(property = "ownerId", column = "owner_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "value", column = "value"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractDetailId", column = "contract_detail_id"),
    })
    List<ContributorIncomesDetail> getContributorIncomesDetail(@Param("ownerId") String ownerId,
                                                               @Param("firstDayOfMonth") String firstDayOfMonth,
                                                               @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select({"<script>" +
            "SELECT " +
            "u.user_id, " +
            "CONVERT(varchar,c.contract_id) contract_id, " +
            "cd.contract_detail_id, " +
            "iv.vehicle_id, " +
            "c.total_price/c.actual_vehicle_count*10/100 driver_earned  " +
            "FROM [user] u " +
            "JOIN user_roles ur " +
            "ON u.user_id = ur.user_id " +
            "JOIN issued_vehicle iv " +
            "ON u.user_id = iv.driver_id  " +
            "JOIN contract_vehicles cv " +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id " +
            "JOIN contract_detail cd  " +
            "ON cv.contract_detail_id = cd.contract_detail_id " +
            "JOIN contract c " +
            "ON cd.contract_id = c.contract_id " +
            "WHERE ur.role_id = 3 " +
            "AND c.contract_status = 'FINISHED' " +
            "AND cd.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' " +
            "<if test = \"driverId!=null\" > " +
            "AND u.user_id = #{driverId}  " +
            "</if>  " +
            "</script>"})
    @Results(id = "driverIncomesResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractDetailId", column = "contract_detail_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "driverEarned", column = "driver_earned"),
    })
    List<DriverIncomes> getDriverIncomes(@Param("driverId") String driverId,
                                         @Param("firstDayOfMonth") String firstDayOfMonth,
                                         @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select("SELECT base_salary " +
            "FROM [user] " +
            "WHERE user_id = #{userId}")
    float getDriverBaseSalary(@Param("userId") String userId);
}
