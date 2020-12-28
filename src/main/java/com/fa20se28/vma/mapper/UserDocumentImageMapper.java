package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserDocumentImageDetail;
import com.fa20se28.vma.request.UserDocumentImageReq;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDocumentImageMapper {
    @Insert("INSERT INTO user_document_image " +
            "(user_document_id, " +
            "image_link, " +
            "create_date)" +
            "VALUES " +
            "(#{documentId}, " +
            "#{UserDocumentImageReq.imageLink}, " +
            "getdate())")
    int insertUserDocumentImage(@Param("UserDocumentImageReq") UserDocumentImageReq userDocumentImageReq,
                                @Param("documentId") int documentId);

    @Update("UPDATE user_document_image " +
            "SET " +
            "image_link = #{UserDocumentImageReq.imageLink}, " +
            "create_date = getdate() " +
            "WHERE user_document_id = #{documentId} " +
            "AND user_document_image_id = #{UserDocumentImageReq.userDocumentImageId} ")
    @Options(keyProperty = "UserDocumentImageReq.userDocumentImageId")
    int updateUserDocumentImage(@Param("UserDocumentImageReq") UserDocumentImageReq userDocumentImageReq,
                                @Param("documentId") String documentId);

//    @Insert("INSERT INTO user_document_image_log " +
//            "(user_document_image_id, " +
//            "user_document_id, " +
//            "image_link, " +
//            "create_date)" +
//            "VALUES " +
//            "(#{UserDocumentImageReq.userDocumentImageId}, " +
//            "#{documentId}, " +
//            "#{UserDocumentImageReq.imageLink}, " +
//            "getdate())")
//    int insertUserDocumentImageLog(@Param("UserDocumentImageReq") UserDocumentImageReq userDocumentImageReq,
//                                   @Param("documentId") String documentId);

    @Delete("Delete user_document_image \n" +
            "WHERE user_document_id = '${user_document_id}'")
    void deleteUserDocumentImage(String userDocumentId);

    @Select("SELECT \n" +
            "user_document_image_id, \n" +
            "user_document_id, \n" +
            "image_link, \n" +
            "create_date\n" +
            "FROM user_document_image \n" +
            "WHERE user_document_id = #{userDocumentId}")
    @Results(id = "userDocumentImageDetailResult", value = {
            @Result(property = "userDocumentImageId", column = "user_document_image_id"),
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "imageLink", column = "image_link"),
            @Result(property = "createDate", column = "create_date")
    })
    List<UserDocumentImageDetail> findUserDocumentImageDetail(@Param("userDocumentId") int userDocumentId);

//    @Select("SELECT \n" +
//            "user_document_image_id, \n" +
//            "user_document_id, \n" +
//            "image_link, \n" +
//            "create_date\n" +
//            "FROM user_document_image_log \n" +
//            "WHERE user_document_id = '${userDocumentId}'")
//    @ResultMap("userDocumentImageDetailResult")
//    List<UserDocumentImageDetail> findUserDocumentImageDetailLog(@Param("userDocumentId") String userDocumentId);

//    @Insert("INSERT INTO user_document_image_log " +
//            "(user_document_image_id, " +
//            "user_document_id, " +
//            "image_link, " +
//            "create_date)" +
//            "VALUES " +
//            "(#{userDocumentImageId}, " +
//            "#{userDocumentId}, " +
//            "#{imageLink}, " +
//            "#{createDate})")
//    int acceptNewUserDocumentImageLog(UserDocumentImageDetail userDocumentImageDetail);

//    @Update("Update user_document_image " +
//            "SET " +
//            "image_link = #{imageLink}, " +
//            "create_date = #{createDate} " +
//            "WHERE user_document_id = '${userDocumentId}' " +
//            "AND user_document_image_id = #{userDocumentImageId} ")
//    int denyNewUserDocumentImageLog(UserDocumentImageDetail userDocumentImageDetail);
}
