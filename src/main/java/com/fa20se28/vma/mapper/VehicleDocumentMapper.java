package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.VehicleDocumentReq;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VehicleDocumentMapper {

    @Select("SELECT " +
            "CASE WHEN " +
            "Count(vd.vehicle_document_id) > 0 THEN 1 " +
            "ELSE 0 END " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_document_id = '%${v_id}%' ")
    boolean isDocumentExist(@Param("v_id") String vehicleDocumentId);

    @Insert("INSERT INTO vehicle_document" +
            "(vehicle_document_id, " +
            "vehicle_id, " +
            "registered_location," +
            "registered_date, " +
            "expiry_date, " +
            "create_date, " +
            "vehicle_document_type) " +
            "VALUES " +
            "(#{d_request.vehicleDocumentId}, " +
            "#{v_id}, " +
            "#{d_request.registeredLocation}, " +
            "#{d_request.registeredDate}, " +
            "#{d_request.expiryDate}, " +
            "getdate(), " +
            "#{d_request.vehicleDocumentType}) ")
    int createVehicleDocument(
            @Param("d_request") VehicleDocumentReq documentReq,
            @Param("v_id") String vehicleId);

    @Update("")
    int updateVehicleDocument();
}
