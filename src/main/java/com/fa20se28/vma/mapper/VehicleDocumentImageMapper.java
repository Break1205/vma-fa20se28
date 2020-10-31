package com.fa20se28.vma.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VehicleDocumentImageMapper {
    @Insert("INSERT INTO vehicle_document_image " +
            "(vehicle_document_id, " +
            "image_link, " +
            "create_date) " +
            "VALUES " +
            "(#{vehicle_document_id}, " +
            "#{image_link}, " +
            "getdate()) ")
    int createVehicleDocumentImage(
            @Param("vehicle_document_id") String documentId,
            @Param("image_link") String imageLink);
}
