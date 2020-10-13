package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserDocumentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDocumentTypeMapper {
    @Select("SELECT udt.user_document_type_id, udt.user_document_type_name " +
            "FROM user_document_type udt ")
    @Results(id = "userDocumentTypeResult",value = {
            @Result(property = "userDocumentTypeId",column = "user_document_type_id"),
            @Result(property = "userDocumentTypeName",column = "user_document_type_name")
    })
    List<UserDocumentType> getUserDocumentTypeList();
}
