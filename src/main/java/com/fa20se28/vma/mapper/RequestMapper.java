package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.model.RequestDetail;
import com.fa20se28.vma.request.ReqInsertReq;
import com.fa20se28.vma.request.RequestPageReq;
import com.fa20se28.vma.response.RequestRes;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RequestMapper {
    @Insert("INSERT INTO " +
            "request " +
            "(user_id, " +
            "user_document_id, " +
            "vehicle_id, " +
            "vehicle_document_id," +
            "contract_trip_id, " +
            "request_status, " +
            "request_type, " +
            "create_date, " +
            "description," +
            "coordinates) " +
            "VALUES " +
            "(#{r_insert.userId}, " +
            "#{r_insert.userDocumentId}, " +
            "#{r_insert.vehicleId}, " +
            "#{r_insert.vehicleDocId}," +
            "#{r_insert.contractTripId}, " +
            "#{r_insert.requestStatus}, " +
            "#{r_insert.requestType}, " +
            "#{r_insert.createDate}, " +
            "#{r_insert.desc}," +
            "#{r_insert.coordinates}) ")
    @Options(keyProperty = "requestId", useGeneratedKeys = true)
    int insertRequest(@Param("r_insert") ReqInsertReq object);

    @Select({"<script>" +
            "SELECT  " +
            "request_id, " +
            "user_id, " +
            "request_type,  " +
            "request_status,  " +
            "description,  " +
            "create_date  " +
            "FROM request " +
            "WHERE 1=1 " +
            "<if test = \"RequestPageReq.userId!=null\" > " +
            "AND user_id LIKE '%${RequestPageReq.userId}%' " +
            "</if>  " +
            "<if test = \"RequestPageReq.requestStatus!=null\" > " +
            "AND request_status LIKE '%${RequestPageReq.requestStatus}%' " +
            "</if>  " +
            "<if test = \"RequestPageReq.requestType!=null\" > " +
            "AND request_type LIKE N'%${RequestPageReq.requestType}%' " +
            "</if>  " +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate!=null\" > " +
            "AND create_date BETWEEN '${RequestPageReq.fromDate}' AND '${RequestPageReq.toDate}' " +
            "</if>  " +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate==null\" > " +
            "AND create_date &gt; '${RequestPageReq.fromDate}'  " +
            "</if>  " +
            "<if test = \"RequestPageReq.fromDate==null and RequestPageReq.toDate!=null\" > " +
            "AND create_date &lt; '${RequestPageReq.toDate}'  " +
            "</if>  " +
            "ORDER BY create_date ${RequestPageReq.sort}  " +
            "OFFSET #{RequestPageReq.page} ROWS  " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "requestsResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "requestType", column = "request_type"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "description", column = "description"),
            @Result(property = "createDate", column = "create_date")
    })
    List<RequestRes> findRequests(@Param("RequestPageReq") RequestPageReq requestPageReq);

    @Select({"<script> " +
            "SELECT  " +
            "COUNT(request_id)  " +
            "FROM request  " +
            "WHERE 1=1  " +
            "<if test = \"RequestPageReq.userId!=null\" >  " +
            "AND user_id LIKE '%${RequestPageReq.userId}%'  " +
            "</if>   " +
            "<if test = \"RequestPageReq.requestStatus!=null\" >  " +
            "AND request_status LIKE '%${RequestPageReq.requestStatus}%'  " +
            "</if>   " +
            "<if test = \"RequestPageReq.requestType!=null\" >  " +
            "AND request_type LIKE N'%${RequestPageReq.requestType}%'  " +
            "</if>   " +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate!=null\" >  " +
            "AND create_date BETWEEN '${RequestPageReq.fromDate}' AND '${RequestPageReq.toDate}'  " +
            "</if>   " +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate==null\" >  " +
            "AND create_date &gt; '${RequestPageReq.fromDate}'   " +
            "</if>   " +
            "<if test = \"RequestPageReq.fromDate==null and RequestPageReq.toDate!=null\" >  " +
            "AND create_date &lt; '${RequestPageReq.toDate}'   " +
            "</if>  " +
            "</script> "})
    int findTotalRequests(@Param("RequestPageReq") RequestPageReq requestPageReq);

    @Select("SELECT   " +
            "request_id,   " +
            "u.user_id,  " +
            "u.full_name, " +
            "user_document_id,   " +
            "vehicle_id,  " +
            "vehicle_document_id," +
            "contract_trip_id, " +
            "request_status,   " +
            "request_type,   " +
            "description," +
            "coordinates,   " +
            "u.create_date  " +
            "FROM request  " +
            "JOIN [user] u " +
            "on request.user_id = u.user_id " +
            "WHERE request_id = #{request_id}")
    @Results(id = "documentRequestResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleDocumentId", column = "vehicle_document_id"),
            @Result(property = "contractTripId", column = "contract_trip_id"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "requestType", column = "request_type"),
            @Result(property = "coordinates", column = "coordinates"),
            @Result(property = "description", column = "description"),
            @Result(property = "createDate", column = "create_date")
    })
    Optional<RequestDetail> findRequestById(@Param("request_id") int requestId);

    @Update("UPDATE request  " +
            "SET request_status = #{requestStatus}  " +
            "WHERE request_id = #{requestId}")
    int updateRequestStatus(@Param("requestId") int requestId, @Param("requestStatus") RequestStatus requestStatus);
}
