package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentImage;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDocumentMapper {
    @Select("SELECT " +
            "ud.user_document_id, " +
            "udt.user_document_type_name, " +
            "ud.user_id, " +
            "ud.registered_location, " +
            "ud.registered_date, " +
            "ud.expiry_date " +
            "FROM user_document ud " +
            "JOIN user_document_type udt " +
            "ON ud.user_document_type_id = udt.user_document_type_id " +
            "WHERE ud.user_id = ${user_id}")
    @Results(id = "userDocumentResult", value = {
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "userDocumentType", column = "user_document_type_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "registerLocation", column = "registered_location"),
            @Result(property = "registerDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "userDocumentImages", column = "user_document_id",
                    many = @Many(select = "findUserDocumentImagesByUserDocumentId")),
    })
    List<UserDocument> findUserDocumentByUserId(@Param("user_id") String userId);

    @Select("SELECT " +
            "udi.user_document_image_id, " +
            "udi.image_link " +
            "FROM user_document_image udi " +
            "WHERE udi.user_document_id = '${user_document_id}' ")
    @Results(id = "userDocumentImageResult",value = {
            @Result(property = "userDocumentImageId", column = "user_document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<UserDocumentImage> findUserDocumentImagesByUserDocumentId(
            @Param("user_document_id") String userDocumentId);
}
