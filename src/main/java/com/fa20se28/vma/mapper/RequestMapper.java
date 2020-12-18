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
            "vehicle_document_id, " +
            "request_status, " +
            "request_type, " +
            "create_date, " +
            "description) " +
            "VALUES " +
            "(#{r_insert.userId}, " +
            "#{r_insert.userDocumentId}, " +
            "#{r_insert.vehicleId}, " +
            "#{r_insert.vehicleDocId}, " +
            "#{r_insert.requestStatus}, " +
            "#{r_insert.requestType}, " +
            "#{r_insert.createDate}, " +
            "#{r_insert.desc}) ")
    @Options(keyProperty = "requestId", useGeneratedKeys = true)
    int insertRequest(@Param("r_insert") ReqInsertReq object);

    @Select({"<script>" +
            "SELECT \n" +
            "request_id,\n" +
            "user_id,\n" +
            "request_type, \n" +
            "request_status, \n" +
            "description, \n" +
            "create_date \n" +
            "FROM request\n" +
            "WHERE 1=1\n" +
            "<if test = \"RequestPageReq.userId!=null\" >\n" +
            "AND user_id LIKE '%${RequestPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"RequestPageReq.requestStatus!=null\" >\n" +
            "AND request_status LIKE '%${RequestPageReq.requestStatus}%'\n" +
            "</if> \n" +
            "<if test = \"RequestPageReq.requestType!=null\" >\n" +
            "AND request_type LIKE N'%${RequestPageReq.requestType}%'\n" +
            "</if> \n" +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate!=null\" >\n" +
            "AND create_date BETWEEN '${RequestPageReq.fromDate}' AND '${RequestPageReq.toDate}'\n" +
            "</if> \n" +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate==null\" >\n" +
            "AND create_date &gt; '${RequestPageReq.fromDate}' \n" +
            "</if> \n" +
            "<if test = \"RequestPageReq.fromDate==null and RequestPageReq.toDate!=null\" >\n" +
            "AND create_date &lt; '${RequestPageReq.toDate}' \n" +
            "</if> \n" +
            "ORDER BY create_date ${RequestPageReq.sort} \n" +
            "OFFSET #{RequestPageReq.page} ROWS \n" +
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

    @Select({"<script> "+
            "SELECT \n" +
            "COUNT(request_id) \n" +
            "FROM request \n" +
            "WHERE 1=1 \n" +
            "<if test = \"RequestPageReq.userId!=null\" > \n" +
            "AND user_id LIKE '%${RequestPageReq.userId}%' \n" +
            "</if>  \n" +
            "<if test = \"RequestPageReq.requestStatus!=null\" > \n" +
            "AND request_status LIKE '%${RequestPageReq.requestStatus}%' \n" +
            "</if>  \n" +
            "<if test = \"RequestPageReq.requestType!=null\" > \n" +
            "AND request_type LIKE N'%${RequestPageReq.requestType}%' \n" +
            "</if>  \n" +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate!=null\" > \n" +
            "AND create_date BETWEEN '${RequestPageReq.fromDate}' AND '${RequestPageReq.toDate}' \n" +
            "</if>  \n" +
            "<if test = \"RequestPageReq.fromDate!=null and RequestPageReq.toDate==null\" > \n" +
            "AND create_date &gt; '${RequestPageReq.fromDate}'  \n" +
            "</if>  \n" +
            "<if test = \"RequestPageReq.fromDate==null and RequestPageReq.toDate!=null\" > \n" +
            "AND create_date &lt; '${RequestPageReq.toDate}'  \n" +
            "</if> \n" +
            "</script> "})
    int findTotalRequests(@Param("RequestPageReq") RequestPageReq requestPageReq);

    @Select("SELECT  \n" +
            "request_id,  \n" +
            "u.user_id, \n" +
            "u.full_name,\n" +
            "user_document_id,  \n" +
            "vehicle_id, \n" +
            "vehicle_document_id,  \n" +
            "request_status,  \n" +
            "request_type,  \n" +
            "description,  \n" +
            "u.create_date \n" +
            "FROM request \n" +
            "JOIN [user] u\n" +
            "on request.user_id = u.user_id\n" +
            "WHERE request_id = #{request_id}")
    @Results(id = "documentRequestResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "vehicleDocumentId", column = "vehicle_document_id"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "requestType", column = "request_type"),
            @Result(property = "description", column = "description"),
            @Result(property = "createDate", column = "create_date")
    })
    Optional<RequestDetail> findRequestById(@Param("request_id") int requestId);

    @Update("UPDATE request \n" +
            "SET request_status = #{requestStatus} \n" +
            "WHERE request_id = #{requestId}")
    int updateRequestStatus(@Param("requestId") int requestId, @Param("requestStatus") RequestStatus requestStatus);
}
