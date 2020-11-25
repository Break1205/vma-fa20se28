package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractDetailReport;
import com.fa20se28.vma.model.ContractReport;
import com.fa20se28.vma.model.ContributorIncome;
import com.fa20se28.vma.model.ContributorIncomesDetail;
import com.fa20se28.vma.model.MaintenanceReport;
import com.fa20se28.vma.model.RevenueExpense;
import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.VehicleReport;
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
            "iv.vehicle_id, \n" +
            "cd.departure_location, \n" +
            "cd.departure_time, \n" +
            "cd.destination_location, \n" +
            "cd.destination_time, \n" +
            "cv.contract_vehicle_status \n" +
            "FROM issued_vehicle iv \n" +
            "JOIN contract_vehicles cv \n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id \n" +
            "JOIN contract_detail cd \n" +
            "ON cv.contract_id = cd.contract_id \n" +
            "WHERE cd.departure_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}'\n" +
            "ORDER BY cd.departure_time ASC")
    @Results(id = "scheduleResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "contractVehicleStatus", column = "contract_vehicle_status"),
    })
    List<Schedule> getListSchedule(@Param("firstDayOfMonth") String firstDayOfMonth,
                                   @Param("lastDayOfMonth") String lastDayOfMonth);

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

    @Select("SELECT\n" +
            "COUNT(v.vehicle_id)\n" +
            "FROM vehicle v \n" +
            "JOIN vehicle_type vt\n" +
            "ON v.vehicle_type_id = vt.vehicle_type_id\n" +
            "JOIN brand b\n" +
            "ON v.brand_id = b.brand_id\n" +
            "JOIN [user] u\n" +
            "ON u.user_id = v.owner_id\n" +
            "WHERE v.vehicle_status != 'DELETED'")
    int getTotalVehicleForReport();

    @Select({"<script>" +
            "SELECT \n" +
            "vehicle_id, \n" +
            "maintenance_type, \n" +
            "start_date, \n" +
            "end_date, \n" +
            "cost,\n" +
            "description\n" +
            "FROM maintenance \n" +
            "WHERE 1 = 1\n" +
            "<if test = \"vehicleId!=null\" > \n" +
            "AND vehicle_id = #{vehicleId} \n" +
            "</if>  \n" +
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

    @Select("SELECT\n" +
            "c.contract_id,\n" +
            "c.total_price contract_value, \n" +
            "c.is_round_trip, \n" +
            "cd.departure_time, \n" +
            "cd.departure_location,\n" +
            "cd.destination_location,\n" +
            "cu.customer_id,\n" +
            "cu.customer_name,\n" +
            "cu.phone_number, \n" +
            "cu.email, \n" +
            "cu.fax, \n" +
            "cu.account_number, \n" +
            "cu.tax_code \n" +
            "FROM contract c \n" +
            "JOIN customer cu \n" +
            "ON c.contract_owner_id = cu.customer_id\n" +
            "JOIN (\n" +
            "SELECT \n" +
            "TOP 1\n" +
            "contract_id, \n" +
            "departure_time, \n" +
            "departure_location, \n" +
            "destination_location \n" +
            "FROM contract_detail \n" +
            "ORDER BY create_date) cd \n" +
            "ON c.contract_id = cd.contract_id\n" +
            "WHERE c.contract_status = 'FINISHED' \n" +
            "AND cd.departure_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
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
                                            @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select("SELECT\n" +
            "cd.destination_time date, \n" +
            "'CONTRACT_REVENUE' type, \n" +
            "c.total_price/c.actual_vehicle_count value, \n" +
            "CONVERT(varchar,c.contract_id) contract_id,\n" +
            "cu.customer_id\n" +
            "FROM issued_vehicle iv\n" +
            "JOIN contract_vehicles cv \n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id \n" +
            "JOIN contract c \n" +
            "ON cv.contract_id = c.contract_id \n" +
            "JOIN (\n" +
            "SELECT\n" +
            "TOP 1 \n" +
            "contract_id,\n" +
            "destination_time\n" +
            "FROM contract_detail\n" +
            "ORDER BY create_date\n" +
            ") cd \n" +
            "ON c.contract_id = cd.contract_id \n" +
            "JOIN customer cu\n" +
            "ON cu.customer_id = c.contract_owner_id\n" +
            "WHERE iv.vehicle_id = #{vehicleId}\n" +
            "AND c.contract_status = 'FINISHED' \n" +
            "AND cd.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "UNION \n" +
            "SELECT \n" +
            "start_date date, \n" +
            "maintenance_type type, \n" +
            "cost value,\n" +
            "'N/A' contract_id,\n" +
            "'N/A' customer_id\n" +
            "FROM maintenance\n" +
            "WHERE vehicle_id = #{vehicleId}\n" +
            "AND is_deleted = 0\n" +
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

    @Select("SELECT\n" +
            "cd.destination_time date, \n" +
            "'CONTRACT REVENUE' type, \n" +
            "c.total_price/c.actual_vehicle_count value, \n" +
            "CONVERT(varchar,c.contract_id) contract_id,\n" +
            "cu.customer_id\n" +
            "FROM issued_vehicle iv\n" +
            "JOIN contract_vehicles cv \n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id \n" +
            "JOIN contract c \n" +
            "ON cv.contract_id = c.contract_id \n" +
            "JOIN (\n" +
            "SELECT\n" +
            "TOP 1 \n" +
            "contract_id,\n" +
            "destination_time\n" +
            "FROM contract_detail\n" +
            "ORDER BY create_date\n" +
            ") cd \n" +
            "ON c.contract_id = cd.contract_id \n" +
            "JOIN customer cu\n" +
            "ON cu.customer_id = c.contract_owner_id\n" +
            "WHERE c.contract_status = 'FINISHED' \n" +
            "AND cd.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "UNION \n" +
            "SELECT \n" +
            "start_date date, \n" +
            "maintenance_type type, \n" +
            "cost value,\n" +
            "'N/A' contract_id,\n" +
            "'N/A' customer_id\n" +
            "FROM maintenance\n" +
            "WHERE is_deleted = 0\n" +
            "AND start_date between '${firstDayOfMonth}' AND '${lastDayOfMonth}' ")
    @ResultMap("revenueExpenseResult")
    List<RevenueExpense> getCompanyRevenueExpenseForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
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
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "value", column = "value"),
            @Result(property = "contractId", column = "contract_id")
    })
    List<ContributorIncome> getContributorIncomeForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                          @Param("lastDayOfMonth") String lastDayOfMonth,
                                                          @Param("contributorId") String contributorId);

    @Select("SELECT \n" +
            "c.contract_id, \n" +
            "c.signed_date, \n" +
            "c.signed_location, \n" +
            "cu.address, \n" +
            "cu.phone_number, \n" +
            "cu.fax, \n" +
            "cu.tax_code,\n" +
            "cu.account_number, \n" +
            "cu.customer_name, \n" +
            "c.estimated_passenger_count, \n" +
            "c.total_price,\n" +
            "cd.departure_location,\n" +
            "cd.departure_time,\n" +
            "cd.destination_location,\n" +
            "cd.destination_time,\n" +
            "c.estimated_vehicle_count,\n" +
            "c.duration_from,\n" +
            "c.duration_to\n" +
            "FROM contract c \n" +
            "JOIN customer cu \n" +
            "ON c.contract_owner_id = cu.customer_id \n" +
            "JOIN (\n" +
            "SELECT \n" +
            "TOP 1\n" +
            "contract_id, \n" +
            "departure_location, \n" +
            "departure_time, \n" +
            "destination_location, \n" +
            "destination_time\n" +
            "FROM contract_detail \n" +
            "ORDER BY create_date \n" +
            ") cd \n" +
            "ON c.contract_id = cd.contract_id\n" +
            "WHERE c.contract_id = #{contractId}")
    @Results(id = "contractDetailReport", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "signedDate", column = "signed_date"),
            @Result(property = "signedLocation", column = "signed_location"),
            @Result(property = "address", column = "address"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "fax", column = "fax"),
            @Result(property = "taxCode", column = "tax_code"),
            @Result(property = "accountNumber", column = "account_number"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "numberOfPassengers", column = "estimated_passenger_count"),
            @Result(property = "contractValue", column = "total_price"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "numberOfVehicles", column = "estimated_vehicle_count"),
            @Result(property = "durationFrom", column = "duration_from"),
            @Result(property = "durationTo", column = "duration_to")
    })
    ContractDetailReport getContractDetailReport(int contractId);


    @Select("SELECT \n" +
            "vv.vehicle_id, \n" +
            "vv.value, \n" +
            "vv.start_date, \n" +
            "vv.end_date, \n" +
            "v.owner_id \n" +
            "FROM \n" +
            "vehicle_value vv\n" +
            "LEFT JOIN vehicle v\n" +
            "ON vv.vehicle_id = v.vehicle_id\n" +
            "WHERE vv.is_deleted = 0 \n" +
            "AND NOT (vv.start_date > '${lastDayOfMonth}' OR vv.end_date < '${firstDayOfMonth}')\n")
    @Results(id = "contributorIncomesResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "value", column = "value"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "ownerId", column = "owner_id")
    })
    List<ContributorIncome> getContributorIncomesForReport(@Param("firstDayOfMonth") String firstDayOfMonth,
                                                           @Param("lastDayOfMonth") String lastDayOfMonth);

    @Select({"<script>" +
            "SELECT \n" +
            "v.owner_id,\n" +
            "v.vehicle_id,\n" +
            "cd.destination_time date,\n" +
            "c.total_price/c.actual_vehicle_count value,\n" +
            "CONVERT(varchar,c.contract_id) contract_id \n" +
            "FROM vehicle v \n" +
            "JOIN issued_vehicle iv \n" +
            "ON v.vehicle_id = iv.vehicle_id\n" +
            "JOIN contract_vehicles cv\n" +
            "ON iv.issued_vehicle_id = cv.issued_vehicle_id\n" +
            "JOIN contract c\n" +
            "ON cv.contract_id = c.contract_id\n" +
            "JOIN ( \n" +
            "SELECT \n" +
            "TOP 1\n" +
            "contract_id, \n" +
            "destination_time \n" +
            "FROM contract_detail \n" +
            "ORDER BY create_date \n" +
            ") cd\n" +
            "ON c.contract_id = cd.contract_id\n" +
            "WHERE c.contract_status = 'FINISHED'\n" +
            "AND cd.destination_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "<if test = \"ownerId!=null\" >\n" +
            "AND v.owner_id = #{ownerId} \n" +
            "</if> " +
            "</script>"})
    @Results(id = "contributorIncomesDetailResult",value = {
            @Result(property = "ownerId",column = "owner_id"),
            @Result(property = "vehicleId",column = "vehicle_id"),
            @Result(property = "date",column = "date"),
            @Result(property = "value",column = "value"),
            @Result(property = "contractId",column = "contract_id"),
    })
    List<ContributorIncomesDetail> getContributorIncomesDetail(@Param("ownerId") String ownerId,
                                                               @Param("firstDayOfMonth") String firstDayOfMonth,
                                                               @Param("lastDayOfMonth") String lastDayOfMonth);
}
