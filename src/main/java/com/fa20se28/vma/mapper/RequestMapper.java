package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.model.DocumentRequestDetail;
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
            "(#{r_u_id}, " +
            "#{r_ud_id}, " +
            "#{r_v_id}, " +
            "#{r_vd_id}, " +
            "#{r_status}, " +
            "#{r_req_type}, " +
            "getDate(), " +
            "#{r_desc}) ")
    int insertRequest(
            @Param("r_u_id") String userId,
            @Param("r_ud_id") String userDocumentId,
            @Param("r_v_id") String vehicleId,
            @Param("r_vd_id") String vehicleDocId,
            @Param("r_status") RequestStatus requestStatus,
            @Param("r_req_type") RequestType requestType,
            @Param("r_desc") String desc);

    @Select({"<script>" +
            "SELECT \n" +
            "request_id,\n" +
            "user_id,\n" +
            "request_type, \n" +
            "request_status, \n" +
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
            "ORDER BY create_date ASC\n" +
            "OFFSET #{RequestPageReq.page} ROWS \n" +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "requestsResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "requestType", column = "request_type"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "createDate", column = "create_date")
    })
    List<RequestRes> findRequests(@Param("RequestPageReq") RequestPageReq requestPageReq);

    @Select({"<script> "+
            "SELECT COUNT(rc.request_id) " +
            "FROM (" +
            "SELECT  \n" +
            "request_id, \n" +
            "user_id, \n" +
            "request_type,  \n" +
            "create_date  \n" +
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
            "</if> ) rc \n" +
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
    Optional<DocumentRequestDetail> findDocumentRequestById(@Param("request_id") int requestId);

    @Update("UPDATE request \n" +
            "SET request_status = #{requestStatus} \n" +
            "WHERE request_id = #{requestId}")
    int updateRequestStatus(@Param("requestId") int requestId, @Param("requestStatus") RequestStatus requestStatus);
}
