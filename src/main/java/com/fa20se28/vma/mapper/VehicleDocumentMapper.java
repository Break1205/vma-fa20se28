package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.request.VehicleDocumentReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleDocumentMapper {

    @Select("SELECT " +
            "CASE WHEN " +
            "Count(vd.vehicle_document_id) > 0 THEN 1 " +
            "ELSE 0 END " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_document_id = #{v_id} ")
    boolean isDocumentExist(@Param("v_id") String vehicleDocumentId);

    @Insert("INSERT INTO vehicle_document " +
            "(vehicle_document_id, " +
            "vehicle_id, " +
            "registered_location," +
            "registered_date, " +
            "expiry_date, " +
            "create_date, " +
            "vehicle_document_type, " +
            "is_deleted) " +
            "VALUES " +
            "(#{d_request.vehicleDocumentId}, " +
            "#{v_id}, " +
            "#{d_request.registeredLocation}, " +
            "#{d_request.registeredDate}, " +
            "#{d_request.expiryDate}, " +
            "getdate(), " +
            "#{d_request.vehicleDocumentType}, " +
            "#{d_option}) ")
    int createVehicleDocument(
            @Param("d_request") VehicleDocumentReq documentReq,
            @Param("v_id") String vehicleId,
            @Param("d_option") boolean createOption);

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
            "is_deleted = 1 " +
            "WHERE " +
            "vehicle_document_id = #{d_id} ")
    int deleteDocument(@Param("d_id") String vehicleDocumentId);
}
