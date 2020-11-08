package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.RequestRes;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RequestMapper {
    @Insert("INSERT INTO \n" +
            "request\n" +
            "(user_id,\n" +
            "user_document_id,\n" +
            "request_status,\n" +
            "request_type,\n" +
            "create_date,\n" +
            "description) \n" +
            "VALUES \n" +
            "(\n" +
            "#{userId},\n" +
            "'${RequestReq.userDocumentReq.userDocumentId}',\n" +
            "'PENDING',\n" +
            "#{RequestReq.requestType},\n" +
            "getDate(),\n" +
            "#{RequestReq.description})")
    int insertRequest(@Param("RequestReq") RequestReq requestReq,
                      @Param("userId") String userId);

    @Select("SELECT \n" +
            "request_id, \n" +
            "user_id, \n" +
            "request_type,\n" +
            "create_date\n" +
            "FROM request \n" +
            "WHERE request_status = 'PENDING' \n" +
            "ORDER BY create_date ASC \n" +
            "OFFSET #{page} ROWS  \n" +
            "FETCH NEXT 15 ROWS ONLY ")
    @Results(id = "pendingRequestsResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "requestType", column = "request_type"),
            @Result(property = "createDate", column = "create_date")
    })
    List<RequestRes> findPendingRequest(int page);

    @Select("SELECT COUNT(ur.request_id) " +
            "FROM (" +
            "SELECT \n" +
            "request_id, \n" +
            "user_id, \n" +
            "request_type,\n" +
            "create_date\n" +
            "FROM request \n" +
            "WHERE request_status = 'PENDING') ur \n")
    int findTotalPendingRequests();

    @Select("SELECT \n" +
            "request_id, \n" +
            "user_id,\n" +
            "user_document_id, \n" +
            "request_status, \n" +
            "request_type, \n" +
            "description, \n" +
            "create_date\n" +
            "FROM request \n" +
            "WHERE request_id = #{request_id}")
    @Results(id = "documentRequestResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "requestStatus", column = "request_status"),
            @Result(property = "requestType", column = "request_type"),
            @Result(property = "description", column = "description"),
            @Result(property = "createDate", column = "create_date")
    })
    Optional<DocumentRequestDetail> findDocumentRequestById(@Param("request_id") int requestId);

    @Update("UPDATE request \n" +
            "SET request_status = #{requestStatus} \n" +
            "WHERE request_id = #{requestId}")
    int updateRequestStatus(@Param("requestId") int requestId,@Param("requestStatus") RequestStatus requestStatus);

    @Insert("INSERT INTO " +
            "request " +
            "(user_id, " +
            "vehicle_id, " +
            "vehicle_document_id, " +
            "request_status, " +
            "request_type, " +
            "create_date, " +
            "description) " +
            "VALUES " +
            "(#{userId}, " +
            "#{r_vehicle_document.vehicleDocument.vehicleId}, " +
            "#{r_vehicle_document.vehicleDocument.vehicleDocumentReq.vehicleDocumentId}, " +
            "#{r_status}, " +
            "#{r_vehicle_document.requestType}, " +
            "getDate(), " +
            "#{r_vehicle_document.description}) ")
    int insertVehicleRequest(
            @Param("r_vehicle_document") VehicleRequestReq requestReq,
            @Param("r_status") RequestStatus requestStatus,
            @Param("userId") String userId);
}
