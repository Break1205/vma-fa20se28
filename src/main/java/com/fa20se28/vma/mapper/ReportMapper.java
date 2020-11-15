package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.ScheduleDetail;
import com.fa20se28.vma.model.VehicleReport;
import com.fa20se28.vma.model.VehicleRevenueExpense;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
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

    @Select("SELECT\n" +
            "c.destination_time date, \n" +
            "'CONTRACT REVENUE' type, \n" +
            "c.total_price value, \n" +
            "CONVERT(varchar,c.contract_id) contract_id,\n" +
            "cu.customer_id\n" +
            "FROM issued_vehicle iv\n" +
            "JOIN contract_vehicles cv \n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id \n" +
            "JOIN contract c \n" +
            "ON cv.contract_id = c.contract_id \n" +
            "JOIN customer cu\n" +
            "ON cu.customer_id = c.contract_owner_id\n" +
            "WHERE iv.vehicle_id = #{vehicleId}\n" +
            "AND c.contract_status = 'FINISHED' \n" +
            "AND c.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "UNION \n" +
            "SELECT \n" +
            "maintenance_date date, \n" +
            "maintenance_type type, \n" +
            "cost value,\n" +
            "'' contract_id,\n" +
            "'' customer_id\n" +
            "FROM maintenance\n" +
            "WHERE vehicle_id = #{vehicleId}\n" +
            "AND maintenance_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @Results(id = "vehicleRevenueExpenseResult", value = {
            @Result(property = "date", column = "date"),
            @Result(property = "type", column = "type"),
            @Result(property = "value", column = "value"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "customerId", column = "customer_id")
    })
    List<VehicleRevenueExpense> getVehicleRevenueExpenseForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                                  @Param("lastDayOfMonth") String lastDayOfMonth,
                                                                  @Param("vehicleId") String vehicleId);
}
