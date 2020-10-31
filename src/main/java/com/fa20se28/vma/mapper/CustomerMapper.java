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
            "customer_name = #{customer_name},\n" +
            "address = #{address},\n" +
            "fax = #{fax},\n" +
            "tax_code = #{tax_code},\n" +
            "phone_number = #{phone_number},\n" +
            "email = #{email},\n" +
            "account_number = #{account_number}\n" +
            "WHERE customer_id = '${customer_id}'")
    void updateCustomer(CustomerReq customerReq);

    void deleteCustomer(String customerId);

    @Select("SELECT " +
            "customer_name " +
            "FROM customer " +
            "WHERE customer_id = #{customerId}")
    @Results(id = "customerResult", value = {
            @Result(property = "customerName", column = "customer_name")
    })
    Optional<Customer> findCustomerByCustomerId(@Param("customerId") String customerId);
}
