package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorIncome;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.ScheduleDetail;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.model.RevenueExpense;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Select("SELECT \n" +
            "c.contract_id, \n" +
            "departure_time date,\n" +
            "departure_location,\n" +
            "destination_location,\n" +
            "total_price contract_value,\n" +
            "contract_status\n" +
            "FROM contract c\n" +
            "WHERE departure_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "ORDER BY date ASC")
    @Results(id = "scheduleResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "contractValue", column = "contract_value"),
            @Result(property = "contractStatus", column = "contract_status"),
            @Result(property = "details", column = "contract_id", many = @Many(select = "getScheduleDetails"))
    })
    List<Schedule> getListSchedule(@Param("firstDayOfMonth") String firstDayOfMonth,
                                   @Param("lastDayOfMonth") String lastDayOfMonth);


    @Select("SELECT \n" +
            "iv.vehicle_id,\n" +
            "u.user_id driver_id,\n" +
            "u.full_name driver_name,\n" +
            "v.owner_id contributor_id\n" +
            "FROM issued_vehicle iv\n" +
            "JOIN vehicle v\n" +
            "ON iv.vehicle_id = v.vehicle_id\n" +
            "JOIN [user] u\n" +
            "ON u.user_id = iv.driver_id \n" +
            "JOIN contract_vehicles cv \n" +
            "ON cv.issued_vehicle_id = iv.issued_vehicle_id \n" +
            "WHERE contract_id = #{contractId}")
    @Results(id = "scheduleDetailResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "driverName", column = "driver_name"),
            @Result(property = "contributorId", column = "contributor_id")
    })
    List<ScheduleDetail> getScheduleDetails(@Param("contractId") String contractId);

    @Select("SELECT \n" +
            "v.vehicle_id,\n" +
            "vt.vehicle_type_name, \n" +
            "b.brand_name, \n" +
            "u.user_id, \n" +
            "u.full_name\n" +
            "FROM vehicle v\n" +
            "JOIN vehicle_type vt \n" +
            "ON v.vehicle_type_id = vt.vehicle_type_id \n" +
            "JOIN brand b \n" +
            "ON v.brand_id = b.brand_id \n" +
            "JOIN [user] u \n" +
            "ON u.user_id = v.owner_id \n" +
            "WHERE v.vehicle_status != 'DELETED' " +
            "ORDER BY u.user_id")
    @Results(id = "vehicleReportResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleType", column = "vehicle_type_name"),
            @Result(property = "brand", column = "brand_name"),
            @Result(property = "ownerId", column = "user_id"),
            @Result(property = "ownerName", column = "full_name")
    })
    List<VehicleReport> getVehiclesForReport();

    @Select("SELECT COUNT(cv.vehicle_id) \n" +
            "FROM (\n" +
            "SELECT \n" +
            "v.vehicle_id,\n" +
            "vt.vehicle_type_name, \n" +
            "b.brand_name, \n" +
            "u.user_id, \n" +
            "u.full_name\n" +
            "FROM vehicle v\n" +
            "JOIN vehicle_type vt \n" +
            "ON v.vehicle_type_id = vt.vehicle_type_id \n" +
            "JOIN brand b \n" +
            "ON v.brand_id = b.brand_id \n" +
            "JOIN [user] u \n" +
            "ON u.user_id = v.owner_id \n" +
            "WHERE v.vehicle_status != 'DELETED') cv")
    int getTotalVehicleForReport();

    @Select("SELECT \n" +
            "maintenance_date, \n" +
            "maintenance_type, \n" +
            "cost\n" +
            "FROM maintenance \n" +
            "WHERE vehicle_id = #{vehicleId}\n" +
            "AND maintenance_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n")
    @Results(id = "maintenanceReportResult", value = {
            @Result(property = "maintenanceDate", column = "maintenance_date"),
            @Result(property = "maintenanceType", column = "maintenance_type"),
            @Result(property = "cost", column = "cost")
    })
    List<MaintenanceReport> getMaintenanceByVehicleIdForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                               @Param("lastDayOfMonth") String lastDayOfMonth,
                                                               @Param("vehicleId") String vehicleId);

    @Select("SELECT \n" +
            "c.destination_time date,\n" +
            "'CONTRACT REVENUE' type,\n" +
            "c.total_price/c.actual_vehicle_count value,\n" +
            "CONVERT(varchar,c.contract_id) contract_id, \n" +
            "cu.customer_id \n" +
            "FROM issued_vehicle iv \n" +
            "JOIN contract_vehicles cv\n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id\n" +
            "JOIN contract c\n" +
            "ON cv.contract_id = c.contract_id\n" +
            "JOIN customer cu \n" +
            "ON cu.customer_id = c.contract_owner_id \n" +
            "WHERE iv.vehicle_id = #{vehicleId} \n" +
            "AND c.contract_status = 'FINISHED'\n" +
            "AND c.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}'\n" +
            "UNION\n" +
            "SELECT\n" +
            "maintenance_date date,\n" +
            "maintenance_type type,\n" +
            "cost value, \n" +
            "'N/A' contract_id, \n" +
            "'N/A' customer_id \n" +
            "FROM maintenance \n" +
            "WHERE vehicle_id = #{vehicleId} \n" +
            "AND maintenance_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
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

    @Select("SELECT \n" +
            "c.destination_time date,\n" +
            "'CONTRACT REVENUE' type,\n" +
            "c.total_price value,\n" +
            "CONVERT(varchar,c.contract_id) contract_id, \n" +
            "cu.customer_id \n" +
            "FROM contract c\n" +
            "JOIN customer cu \n" +
            "ON cu.customer_id = c.contract_owner_id \n" +
            "WHERE c.contract_status = 'FINISHED'\n" +
            "AND c.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "UNION\n" +
            "SELECT\n" +
            "maintenance_date date,\n" +
            "maintenance_type type,\n" +
            "cost value, \n" +
            "'N/A' contract_id, \n" +
            "'N/A' customer_id \n" +
            "FROM maintenance \n" +
            "WHERE maintenance_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @ResultMap("revenueExpenseResult")
    List<RevenueExpense> getCompanyRevenueExpenseForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                           @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select("SELECT \n" +
            "contract_id, \n" +
            "total_price contract_value,\n" +
            "departure_location, \n" +
            "destination_location, \n" +
            "destination_time, \n" +
            "cu.customer_id, \n" +
            "cu.customer_name, \n" +
            "cu.phone_number,\n" +
            "cu.email,\n" +
            "cu.fax,\n" +
            "cu.account_number,\n" +
            "cu.tax_code\n" +
            "FROM contract c\n" +
            "JOIN customer cu \n" +
            "ON c.contract_owner_id = cu.customer_id \n" +
            "WHERE c.contract_status = 'FINISHED'\n" +
            "AND c.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @Results(id = "contractReportResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractValue", column = "contract_value"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "email", column = "email"),
            @Result(property = "fax", column = "fax"),
            @Result(property = "accountNumber", column = "account_number"),
            @Result(property = "taxCode", column = "tax_code"),
    })
    List<ContractReport> getContractsReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                            @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select("SELECT\n" +
            "v.vehicle_id,\n" +
            "c.destination_time date, \n" +
            "c.total_price/c.actual_vehicle_count*25/100 value, \n" +
            "CONVERT(varchar,c.contract_id) contract_id \n" +
            "FROM issued_vehicle iv\n" +
            "JOIN contract_vehicles cv \n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id \n" +
            "JOIN contract c \n" +
            "ON cv.contract_id = c.contract_id \n" +
            "LEFT JOIN vehicle v \n" +
            "on v.vehicle_id = iv.vehicle_id\n" +
            "WHERE v.owner_id = #{contributorId}\n" +
            "AND c.contract_status = 'FINISHED' \n" +
            "AND c.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @Results(id = "contributorIncomeResult", value = {
            @Result(property = "vehicleId",column = "vehicle_id"),
            @Result(property = "date",column = "date"),
            @Result(property = "value",column = "value"),
            @Result(property = "contractId",column = "contract_id")
    })
    List<ContributorIncome> getContributorIncomesForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                           @Param("lastDayOfMonth") String lastDayOfMonth,
                                                           @Param("contributorId") String contributorId);
}
