package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.model.Contract;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.model.ContractTrip;
import com.fa20se28.vma.model.ContractTripSchedule;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContractMapper {
    @Select("SELECT  " +
            "contract_id, " +
            "contract_status  " +
            "FROM contract  " +
            "WHERE contract_owner_id = '${contract_owner_id}'  " +
            "AND contract_status != 'FINISHED'")
    @Results(id = "unfinishedContractResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractStatus", column = "contract_status")
    })
    List<Contract> getUnfinishedContractByContractOwnerId(@Param("contract_owner_id") String contractOwnerId);

    @Insert("INSERT INTO contract " +
            "(signed_date," +
            "signed_location, " +
            "duration_from, " +
            "duration_to, " +
            "contract_owner_id, " +
            "contract_status, " +
            "passenger_count, " +
            "vehicle_count, " +
            "total_price, " +
            "is_round_trip, " +
            "other_information, " +
            "create_date) " +
            "VALUES " +
            "(#{c_request.signedDate}, " +
            "#{c_request.signedLocation}, " +
            "#{c_request.durationFrom}, " +
            "#{c_request.durationTo}, " +
            "#{c_request.contractOwnerId}, " +
            "#{c_status}, " +
            "#{c_request.passengerCount}, " +
            "#{c_request.vehicleCount}, " +
            "#{c_request.totalPrice}, " +
            "#{c_request.isRoundTrip}, " +
            "#{c_request.otherInformation}, " +
            "getDate()) ")
    @Options(keyProperty = "c_request.contractId", useGeneratedKeys = true)
    int createContract(
            @Param("c_request") ContractReq contractReq,
            @Param("c_status") ContractStatus status);

    @Select("SELECT TOP 1 contract_id " +
            "FROM contract " +
            "WHERE " +
            "contract_owner_id = #{c_owner_id} " +
            "ORDER BY create_date DESC ")
    int getContractId(@Param("c_owner_id") String contractOwnerId);

    @Update("UPDATE contract " +
            "SET " +
            "signed_date = #{c_request.signedDate},  " +
            "signed_location = #{c_request.signedLocation},  " +
            "duration_from = #{c_request.durationFrom},  " +
            "duration_to = #{c_request.durationTo},  " +
            "contract_owner_id = #{c_request.contractOwnerId},  " +
            "passenger_count = #{c_request.passengerCount}, " +
            "vehicle_count = #{c_request.vehicleCount}, " +
            "total_price = #{c_request.totalPrice},  " +
            "is_round_trip = #{c_request.isRoundTrip},  " +
            "other_information = #{c_request.otherInformation} " +
            "WHERE " +
            "contract_id = #{c_request.contractId} ")
    int updateContract(@Param("c_request") ContractReq contractUpdateReq);

    @Select({"<script> " +
            "SELECT " +
            "c.contract_id, c.contract_status, c.duration_from, c.duration_to, c.total_price " +
            "FROM contract c " +
            "WHERE " +
            "<if test = \"c_option == 0\" > " +
            "c.contract_status != 'CANCELLED' " +
            "</if> " +
            "<if test = \"c_option == 1\" > " +
            "c.contract_status = #{c_request.contractStatus} " +
            "</if> " +
            "<if test = \"c_request.durationFrom != null\" > " +
            "AND c.duration_from &gt;= #{c_request.durationFrom} " +
            "</if> " +
            "<if test = \"c_request.durationTo != null\" > " +
            "AND c.duration_to &lt;= #{c_request.durationTo} " +
            "</if> " +
            "<if test = \"c_request.totalPriceMin != 0\" > " +
            "AND c.total_price &gt;= #{c_request.totalPriceMin} " +
            "</if> " +
            "<if test = \"c_request.totalPriceMax != 0\" > " +
            "AND c.total_price &lt;= #{c_request.totalPriceMax} " +
            "</if> " +
            "ORDER BY c.create_date DESC " +
            "OFFSET ${c_offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script> "})
    @Results(id = "contracts", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractStatus", column = "contract_status"),
            @Result(property = "durationFrom", column = "duration_from"),
            @Result(property = "durationTo", column = "duration_to"),
            @Result(property = "totalPrice", column = "total_price")
    })
    List<ContractLM> getContracts(
            @Param("c_request") ContractPageReq contractPageReq,
            @Param("c_option") int viewOption,
            @Param("c_offset") int offset);

    @Select({"<script> " +
            "SELECT " +
            "COUNT (c.contract_id) " +
            "FROM contract c " +
            "WHERE " +
            "<if test = \"c_option == 0\" > " +
            "c.contract_status != 'CANCELLED' " +
            "</if> " +
            "<if test = \"c_option == 1\" > " +
            "c.contract_status = #{c_request.contractStatus} " +
            "</if> " +
            "<if test = \"c_request.durationFrom != null\" > " +
            "AND c.duration_from &gt;= #{c_request.durationFrom} " +
            "</if> " +
            "<if test = \"c_request.durationTo != null\" > " +
            "AND c.duration_to &lt;= #{c_request.durationTo} " +
            "</if> " +
            "<if test = \"c_request.totalPriceMin != 0\" > " +
            "AND c.total_price &gt;= #{c_request.totalPriceMin} " +
            "</if> " +
            "<if test = \"c_request.totalPriceMax != 0\" > " +
            "AND c.total_price &lt;= #{c_request.totalPriceMax} " +
            "</if> " +
            "</script> "})
    int getContractCount(
            @Param("c_request") ContractPageReq contractPageReq,
            @Param("c_option") int viewOption);

    @Select("SELECT " +
            "c.contract_id, ctm.customer_id, ctm.customer_name, c.signed_date, c.signed_location, " +
            "c.duration_from, c.duration_to, c.contract_status, " +
            "c.passenger_count, c.vehicle_count, " +
            "c.is_round_trip, c.total_price, c.other_information " +
            "FROM contract c " +
            "JOIN customer ctm ON ctm.customer_id = c.contract_owner_id " +
            "WHERE " +
            "c.contract_id = #{c_id} ")
    @Results(id = "contractDetails", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractOwner.userId", column = "customer_id"),
            @Result(property = "contractOwner.userName", column = "customer_name"),
            @Result(property = "signedDate", column = "signed_date"),
            @Result(property = "signedLocation", column = "signed_location"),
            @Result(property = "durationFrom", column = "duration_from"),
            @Result(property = "durationTo", column = "duration_to"),
            @Result(property = "contractStatus", column = "contract_status"),
            @Result(property = "passengerCount", column = "passenger_count"),
            @Result(property = "vehicleCount", column = "vehicle_count"),
            @Result(property = "isRoundTrip", column = "is_round_trip"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "otherInformation", column = "other_information"),
            @Result(property = "trips", column = "contract_id", many = @Many(select = "getContractTrips"))
    })
    ContractDetail getContractDetails(@Param("c_id") int contractId);

    @Select("SELECT " +
            "contract_trip_id, departure_time, destination_time, departure_location, destination_location " +
            "FROM contract_trip " +
            "WHERE contract_id = #{c_id} " +
            "ORDER BY contract_trip_id ASC ")
    @Results(id = "contractTrips", value = {
            @Result(property = "contractTripId", column = "contract_trip_id"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time"),
            @Result(property = "locations", column = "contract_trip_id", many = @Many(select = "getContractTripSchedule")),
            @Result(property = "assignedVehicles", column = "contract_trip_id", many = @Many(select = "getAssignedVehicles"))
    })
    List<ContractTrip> getContractTrips(@Param("c_id") int contractId);

    @Select("SELECT " +
            "contract_trip_schedule_id, location " +
            "FROM contract_trip_schedule " +
            "WHERE contract_trip_id = #{cd_id} " +
            "ORDER BY contract_trip_schedule_id ASC ")
    @Results(id = "tripSchedule", value = {
            @Result(property = "locationId", column = "contract_trip_schedule_id")})
    List<ContractTripSchedule> getContractTripSchedule(@Param("cd_id") int contractDetailId);

    @Select("SELECT  " +
            "iv.vehicle_id " +
            "FROM contract_vehicles cv  " +
            "JOIN issued_vehicle iv  " +
            "ON cv.issued_vehicle_id = iv.issued_vehicle_id " +
            "WHERE contract_trip_id = #{cd_id} " +
            "ORDER BY contract_trip_id ASC")
    List<String> getAssignedVehicles(@Param("cd_id") int contractDetailId);

    @Update("UPDATE contract " +
            "SET contract_status = #{c_status} " +
            "WHERE contract_id = #{c_id} ")
    int updateStatus(
            @Param("c_status") ContractStatus contractStatus,
            @Param("c_id") int contractId);

    @Update("UPDATE contract " +
            "SET " +
            "passenger_count = #{c_apc}, " +
            "vehicle_count = #{c_avc} " +
            "WHERE " +
            "contract_id = #{c_id} ")
    int updateContractActual(
            @Param("c_apc") int actualPassengerCount,
            @Param("c_avc") int actualVehicleCount,
            @Param("c_id") int contractId);

    @Select("SELECT total_price " +
            "FROM contract_trip ct  " +
            "JOIN contract c  " +
            "ON ct.contract_id = c.contract_id  " +
            "WHERE contract_trip_id = #{contractTripId} ")
    float getContractValueByContractId(@Param("contractTripId") int contractTripId);

    @Select("SELECT total_price, vehicle_count " +
            "FROM contract_trip ct  " +
            "JOIN contract c  " +
            "ON ct.contract_id = c.contract_id  " +
            "WHERE contract_trip_id = #{contractTripId} ")
    @Results(id = "priceAndNumberOfVehiclesResult", value = {
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "vehicleCount", column = "vehicle_count"),
    })
    ContractDetail getContractValueAndNumberOfVehiclesByContractTripId(int contractTripId);
}
