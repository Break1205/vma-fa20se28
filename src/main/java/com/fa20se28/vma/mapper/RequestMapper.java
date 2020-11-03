package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.RequestReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RequestMapper {
    @Insert("INSERT INTO \n" +
            "request\n" +
            "(user_id,\n" +
            "user_document_id,\n" +
            "request_status,\n" +
            "request_type,\n" +
            "create_date,\n" +
            "description) \n" +
            "VALUES \n" +
            "(\n" +
            "#{userId},\n" +
            "#{userDocumentId},\n" +
            "'PENDING',\n" +
            "#{RequestReq.requestType},\n" +
            "getDate(),\n" +
            "#{RequestReq.description})")
    int insertRequest(@Param("RequestReq") RequestReq requestReq,
                      @Param("userDocumentId") String userDocumentId,
                      @Param("userId") String userId);

}
