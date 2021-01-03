package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.model.VehicleDocumentImage;
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
            "getDate(), " +
            "#{d_request.vehicleDocumentType}, " +
            "#{d_status}) ")
    @Options(keyProperty = "d_request.vehicleDocumentId", useGeneratedKeys = true)
    int createVehicleDocument(
            @Param("d_request") VehicleDocumentReq documentReq,
            @Param("v_id") String vehicleId,
            @Param("d_status") DocumentStatus status);

    @Update("UPDATE vehicle_document " +
            "SET " +
            "vehicle_document_number = #{d_request.vehicleDocumentNumber}, " +
            "vehicle_document_type = #{d_request.vehicleDocumentType}, " +
            "registered_location = #{d_request.registeredLocation}, " +
            "registered_date = #{d_request.registeredDate}, " +
            "expiry_date = #{d_request.expiryDate} " +
            "WHERE vehicle_document_id = #{d_request.vehicleDocumentId} ")
    int updateVehicleDocument(@Param("d_request") VehicleDocumentUpdateReq documentReq);

    @Select("SELECT " +
            "vd.vehicle_document_id, " +
            "vd.vehicle_document_number, " +
            "vd.vehicle_document_type, " +
            "vd.status, " +
            "vd.registered_location, " +
            "vd.registered_date, " +
            "vd.expiry_date " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_id = #{v_id} " +
            "AND vd.status = #{d_status} ")
    @Results(id = "vehicleDocuments", value = {
            @Result(property = "vehicleDocumentId", column = "vehicle_document_id"),
            @Result(property = "vehicleDocumentNumber", column = "vehicle_document_number"),
            @Result(property = "vehicleDocumentType", column = "vehicle_document_type"),
            @Result(property = "registeredLocation", column = "registered_location"),
            @Result(property = "registeredDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "imageLinks", column = "vehicle_document_id", many = @Many(select = "getImageLinks"))
    })
    List<VehicleDocument> getVehicleDocuments(
            @Param("v_id") String vehicleId,
            @Param("d_option") int viewOption,
            @Param("d_status") DocumentStatus status);

    @Select("SELECT vdi.vehicle_document_image_id, vdi.image_link " +
            "FROM vehicle_document_image vdi " +
            "WHERE vdi.vehicle_document_id = #{d_id} ")
    @Results(id = "vehicleDocumentImage", value = {
            @Result(property = "vehicleDocumentImageId", column = "vehicle_document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<VehicleDocumentImage> getImageLinks(@Param("d_id") String documentId);

    @Update("UPDATE vehicle_document " +
            "SET status = #{d_status} " +
            "WHERE vehicle_document_id = #{d_id} ")
    int updateDocumentStatus(
            @Param("d_id") int vehicleDocumentId,
            @Param("d_status") DocumentStatus status);

    @Select("SELECT " +
            "vd.vehicle_document_id, " +
            "vd.vehicle_document_number, " +
            "vd.vehicle_document_type, " +
            "vd.status, " +
            "vd.registered_location, " +
            "vd.registered_date, " +
            "vd.expiry_date " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_document_id = #{d_id} ")
    @ResultMap("vehicleDocuments")
    VehicleDocument getVehicleDocumentById(@Param("d_id") int vehicleDocId);

    @Update("UPDATE vehicle_document " +
            "SET " +
            "status = 'DELETED' " +
            "WHERE vehicle_id = #{v_id} " +
            "AND status = 'VALID' ")
    int deleteVehicleDocuments(@Param("v_id") String vehicleId);

    @Select("SELECT TOP 1 vd.vehicle_document_id " +
            "FROM vehicle_document vd " +
            "WHERE vd.vehicle_document_number = #{d_num} " +
            "AND vd.status = 'VALID' " +
            "ORDER BY vd.create_date ")
    int getCurrentIdOfVehicleDocument(@Param("d_num") String vehicleDocumentNumber);

    @Update("UPDATE vehicle_document " +
            "SET status = #{d_status} " +
            "WHERE vehicle_id = #{v_id} " +
            "AND status = 'PENDING' ")
    int changeVehicleDocumentsStatusFromRequest(
            @Param("v_id") String vehicleId,
            @Param("d_status") DocumentStatus status);
}
