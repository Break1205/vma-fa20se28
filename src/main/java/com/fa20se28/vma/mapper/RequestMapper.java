package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Request;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestMapper {

    @Insert("INSERT INTO request " +
            "(user_id, " +
            "request_status_id, " +
            "request_date, " +
            "description, " +
            "is_deleted) " +
            "VALUES " +
            "(#{userId}, " +
            "#{requestStatusId}, " +
            "getdate(), " +
            "#{description}, " +
            "#{isDeleted})")
    int insertRequest(Request request);


}
