package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Customer;
import com.fa20se28.vma.request.CustomerReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface CustomerMapper {
    @Insert("INSERT INTO customer\n" +
            "(customer_id,\n" +
            "customer_name,\n" +
            "address,\n" +
            "fax,\n" +
            "tax_code,\n" +
            "phone_number,\n" +
            "email,\n" +
            "account_number,\n" +
            "is_deleted,\n" +
            "create_date)\n" +
            "VALUES\n" +
            "(#{CustomerReq.customerId},\n" +
            "#{CustomerReq.customerName},\n" +
            "#{CustomerReq.address},\n" +
            "#{CustomerReq.fax},\n" +
            "#{CustomerReq.taxCode},\n" +
            "#{CustomerReq.phoneNumber},\n" +
            "#{CustomerReq.email},\n" +
            "#{CustomerReq.accountNumber},\n" +
            "0,\n" +
            "getDate())")
    int insertCustomer(@Param("CustomerReq") CustomerReq customerReq);

    @Update("UPDATE customer\n" +
            "SET \n" +
            "customer_name = #{CustomerReq.customerName},\n" +
            "address = #{CustomerReq.address},\n" +
            "fax = #{CustomerReq.fax},\n" +
            "tax_code = #{CustomerReq.taxCode},\n" +
            "phone_number = #{CustomerReq.phoneNumber},\n" +
            "email = #{CustomerReq.email},\n" +
            "account_number = #{CustomerReq.accountNumber}\n" +
            "WHERE customer_id = '${CustomerReq.customerId}'")
    void updateCustomer(@Param("CustomerReq") CustomerReq customerReq);

    @Update("UPDATE customer " +
            "SET " +
            "is_deleted = 1 " +
            "WHERE customer_id = '${customerId}'")
    void deleteCustomer(@Param("customerId") String customerId);

    @Select("SELECT " +
            "customer_name " +
            "FROM customer " +
            "WHERE customer_id = #{customerId}")
    @Results(id = "customerResult", value = {
            @Result(property = "customerName", column = "customer_name")
    })
    Optional<Customer> findCustomerByCustomerId(@Param("customerId") String customerId);
}
