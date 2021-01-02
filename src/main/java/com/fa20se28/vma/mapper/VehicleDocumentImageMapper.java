package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleDocumentImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
            "SET image_link = #{d_images.imageLink} " +
            "WHERE vehicle_document_image_id = #{d_images.vehicleDocumentImageId} " )
    int updateVehicleDocumentImage(
            @Param("vd_id") int documentId,
            @Param("d_images") VehicleDocumentImage image);
}
