package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.request.VehicleDocumentReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleDocumentMapper {

    @Select("SELECT " +
            "CASE WHEN " +
            "Count(vd.vehicle_document_number) > 0 THEN 1 " +
            "ELSE 0 END " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_document_number = #{d_num} " +
            "AND vd.status = 'VALID' ")
    boolean isDocumentExist(@Param("d_num") String vehicleDocumentNumber);

    @Insert("INSERT INTO vehicle_document " +
            "(vehicle_document_number, " +
            "vehicle_id, " +
            "registered_location," +
            "registered_date, " +
            "expiry_date, " +
            "create_date, " +
            "vehicle_document_type, " +
            "status) " +
            "VALUES " +
            "(#{d_request.vehicleDocumentNumber}, " +
            "#{v_id}, " +
            "#{d_request.registeredLocation}, " +
            "#{d_request.registeredDate}, " +
            "#{d_request.expiryDate}, " +
            "getdate(), " +
            "#{d_request.vehicleDocumentType}, " +
            "#{d_status}) ")
    @Options(keyProperty = "d_request.vehicleDocumentId", useGeneratedKeys = true)
    int createVehicleDocument(
            @Param("d_request") VehicleDocumentReq documentReq,
            @Param("v_id") String vehicleId,
            @Param("d_status") DocumentStatus status);

    @Update("UPDATE vehicle_document " +
            "SET " +
            "registered_location = #{d_request.registeredLocation}, " +
            "registered_date = #{d_request.registeredDate}, " +
            "expiry_date = #{d_request.expiryDate}, " +
            "vehicle_document_type = #{d_request.vehicleDocumentType} " +
            "WHERE " +
            "vehicle_document_id = #{d_request.vehicleDocumentId} ")
    int updateVehicleDocument(@Param("d_request") VehicleDocumentUpdateReq documentReq);

    @Select({"<script> " +
            "SELECT " +
            "vd.vehicle_document_id, " +
            "vd.vehicle_document_type, " +
            "vd.registered_location, " +
            "vd.registered_date, " +
            "vd.expiry_date " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_id = #{v_id} " +
            "<if test = \"d_option == 1\" > " +
            "AND is_deleted = 0 " +
            "</if> " +
            "<if test = \"d_option == 2\" > " +
            "AND is_deleted = 1 " +
            "</if> " +
            "</script> "})
    @Results(id = "vehicleDocuments", value = {
            @Result(property = "vehicleDocumentId", column = "vehicle_document_id"),
            @Result(property = "vehicleDocumentType", column = "vehicle_document_type"),
            @Result(property = "registeredLocation", column = "registered_location"),
            @Result(property = "registeredDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date")
    })
    List<VehicleDocument> getVehicleDocuments(
            @Param("v_id") String vehicleId,
            @Param("d_option") int viewOption);

    @Update("UPDATE vehicle_document " +
            "SET " +
            "is_deleted = #{d_option} " +
            "WHERE " +
            "vehicle_document_id = #{d_id} ")
    int updateDocumentStatus(
            @Param("d_id") String vehicleDocumentId,
            @Param("d_option") boolean option);

    @Select("SELECT " +
            "vd.vehicle_document_id, " +
            "vd.vehicle_document_type, " +
            "vd.registered_location, " +
            "vd.registered_date, " +
            "vd.expiry_date " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_document_id = #{d_id} ")
    @ResultMap("vehicleDocuments")
    VehicleDocument getVehicleDocumentById(
            @Param("d_id") String documentId);

    @Select("SELECT is_deleted " +
            "FROM vehicle_document " +
            "WHERE vehicle_document_id = #{d_id} ")
    boolean checkIfDocumentIsInUse(@Param("d_id") String documentId);

    @Insert("INSERT INTO vehicle_document_log " +
            "(vehicle_document_id, " +
            "vehicle_id, " +
            "registered_location," +
            "registered_date, " +
            "expiry_date, " +
            "vehicle_document_type, " +
            "request_id) " +
            "VALUES " +
            "(#{doc.vehicleDocumentId}, " +
            "#{v_id}, " +
            "#{doc.registeredLocation}, " +
            "#{doc.registeredDate}, " +
            "#{doc.expiryDate}, " +
            "#{doc.vehicleDocumentType}," +
            "#{r_id}) ")
    int moveDeniedVehicleDocumentToLog(
            @Param("doc") VehicleDocument vDocument,
            @Param("v_id") String vehicleId,
            @Param("r_id") int requestId);

    @Update("UPDATE vehicle_document " +
            "SET " +
            "status = 'DELETED' " +
            "WHERE vehicle_id = #{v_id} " +
            "AND status = 'VALID' ")
    int deleteVehicleDocuments(@Param("v_id") String vehicleId);

    @Update("UPDATE vehicle_document " +
            "SET " +
            "registered_location = #{d_request.registeredLocation}, " +
            "registered_date = #{d_request.registeredDate}, " +
            "expiry_date = #{d_request.expiryDate}, " +
            "vehicle_document_type = #{d_request.vehicleDocumentType} " +
            "WHERE " +
            "vehicle_document_id = #{d_request.vehicleDocumentId} ")
    int updateVehicleDocumentStatus(
            @Param("v_id") String vehicleId,
            @Param("d_status") DocumentStatus status);
}
