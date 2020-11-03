package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.model.Contract;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContractMapper {
    @Select("SELECT \n" +
            "contract_id,\n" +
            "contract_status \n" +
            "FROM contract \n" +
            "WHERE contract_owner_id = '${contract_owner_id}' \n" +
            "AND contract_status != 'FINISHED'")
    @Results(id = "unfinishedContractResult", value = {
            @Result(property = "contractId",column = "contract_id"),
            @Result(property = "contractStatus",column = "contract_status")
    })
    List<Contract> getUnfinishedContractByContractOwnerId(@Param("contract_owner_id") String contractOwnerId);

    @Insert("INSERT INTO contract " +
            "(signed_date," +
            "signed_location, " +
            "duration_from, " +
            "duration_to, " +
            "contract_owner_id, " +
            "contract_status, " +
            "departure_location, " +
            "departure_time, " +
            "destination_location, " +
            "destination_time, " +
            "total_price, " +
            "other_information, " +
            "create_date) " +
            "VALUES " +
            "(#{c_request.signedDate}, " +
            "#{c_request.signedLocation}, " +
            "#{c_request.durationFrom}, " +
            "#{c_request.durationTo}, " +
            "#{c_request.contractOwnerId}, " +
            "#{c_status}, " +
            "#{c_request.departureLocation}, " +
            "#{c_request.departureTime}, " +
            "#{c_request.destinationLocation}, " +
            "#{c_request.destinationTime}, " +
            "#{c_request.totalPrice}, " +
            "#{c_request.otherInformation}, " +
            "getdate()) ")
    int createContract(
            @Param("c_request") ContractReq contractReq,
            @Param("c_status") ContractStatus status);

    @Select({"<script> " +
            "SELECT " +
            "c.contract_id, ctm.customer_name, c.contract_status, c.duration_from, c.duration_to, c.total_price " +
            "FROM contract c " +
            "JOIN customer ctm ON c.contract_owner_id = ctm.customer_id " +
            "WHERE " +
            "<if test = \"c_option == 0\" > " +
            "c.contract_status != 'CANCELLED' " +
            "</if> " +
            "<if test = \"c_option == 1\" > " +
            "c.contract_status = #{c_request.contractStatus} " +
            "</if> " +
            "<if test = \"c_request.contractOwnerId != null\" > " +
            "AND c.contract_owner_id LIKE '%${c_request.contractOwnerId}%' " +
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
            "ORDER BY c.signed_date DESC " +
            "OFFSET ${c_offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script> "})
    @Results(id = "contracts", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "contractOwnerName", column = "customer_name"),
            @Result(property = "contractStatus", column = "contract_status"),
            @Result(property = "durationFrom", column = "duration_from"),
            @Result(property = "durationTo", column = "duration_to"),
            @Result(property = "totalPrice", column = "total_price")
    })
    List<ContractLM> getContracts(
            @Param("c_request") ContractPageReq contractPageReq,
            @Param("c_option") int viewOption,
            @Param("c_offset") int offset);
}
