package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleDocumentImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleDocumentImageMapper {
    @Insert("INSERT INTO vehicle_document_image " +
            "(vehicle_document_id, " +
            "image_link, " +
            "create_date) " +
            "VALUES " +
            "(#{vd_id}, " +
            "#{image_link}, " +
            "getDate()) ")
    int createVehicleDocumentImage(
            @Param("vd_id") int vehicleDocumentId,
            @Param("image_link") String imageLink);

    @Update("UPDATE vehicle_document_image " +
            "SET " +
            "image_link = #{d_images.imageLink} " +
            "WHERE " +
            "vehicle_document_id = #{vehicle_document_id} " +
            "AND vehicle_document_image_id = #{d_images.vehicleDocumentImageId} " )
    int updateVehicleDocumentImage(
            @Param("vehicle_document_id") String documentId,
            @Param("d_images") VehicleDocumentImage image);

    @Select({"<script> " +
            "SELECT vdi.vehicle_document_image_id, vdi.image_link " +
            "FROM vehicle_document_image vdi " +
            "WHERE vdi.vehicle_document_id = #{v_document_id} " +
            "<if test = \"v_doc_option == 0\" > " +
            "AND is_deleted = 0 " +
            "</if> " +
            "<if test = \"v_doc_option == 1\" > " +
            "AND is_deleted = 1 " +
            "</if> " +
            "</script> "})
    @Results(id = "vehicleDocumentImage", value = {
            @Result(property = "vehicleDocumentImageId", column = "vehicle_document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<VehicleDocumentImage> getImageLinks(
            @Param("v_document_id") String documentId,
            @Param("v_doc_option") int viewOption);

    @Update("UPDATE vehicle_document_image " +
            "SET is_deleted = 1 " +
            "WHERE vehicle_document_image_id = #{vdi_id} ")
    int deleteImages(@Param("vdi_id") int vehicleDocumentImageId);

    @Insert("INSERT INTO vehicle_document_image_log " +
            "(vehicle_document_image_id, " +
            "request_id) " +
            "VALUES " +
            "(#{vdi_id}, " +
            "#{r_id}) ")
    int moveDeniedVehicleDocumentImageToLog(
            @Param("vdi_id") int vehicleDocumentImageId,
            @Param("r_id") int requestId);
}
