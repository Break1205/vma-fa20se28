package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Customer;
import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerRes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
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

    @Select("SELECT \n" +
            "customer_id,\n" +
            "customer_name,\n" +
            "address,\n" +
            "fax,\n" +
            "tax_code,\n" +
            "phone_number,\n" +
            "email,\n" +
            "account_number\n" +
            "FROM customer\n" +
            "WHERE customer_id = '${customerId}'")
    @Results(id = "customerResult", value = {
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "address", column = "address"),
            @Result(property = "fax", column = "fax"),
            @Result(property = "taxCode", column = "tax_code"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "email", column = "email"),
            @Result(property = "accountNumber", column = "account_number"),
    })
    Optional<Customer> findCustomerByCustomerId(@Param("customerId") String customerId);

    @Select({"<script>" +
            "SELECT \n" +
            "customer_id,\n" +
            "customer_name,\n" +
            "address,\n" +
            "email,\n" +
            "phone_number\n" +
            "FROM customer\n" +
            "WHERE \n" +
            "<if test = \"CustomerPageReq.isDeleted==null\" >\n" +
            "is_deleted = 0 \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.isDeleted==1\" >\n" +
            "is_deleted = 1 \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.isDeleted==0\" >\n" +
            "is_deleted = 0 \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.isDeleted!=null\" >\n" +
            "<if test = \"CustomerPageReq.isDeleted!=0 and CustomerPageReq.isDeleted!=1\" >\n" +
            "is_deleted = #{CustomerPageReq.isDeleted} \n" +
            "</if> \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.customerName!=null\" >\n" +
            "AND customer_name LIKE N'%${CustomerPageReq.customerName}%' \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.address!=null\" >\n" +
            "AND address LIKE N'%${CustomerPageReq.address}%' \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.email!=null\" >\n" +
            "AND email LIKE '%${CustomerPageReq.email}%' \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.phoneNumber!=null\" >\n" +
            "AND phone_number LIKE N'%${CustomerPageReq.phoneNumber}%' \n" +
            "</if> \n" +
            "ORDER BY customer_id ASC\n" +
            "OFFSET ${CustomerPageReq.page} ROWS \n" +
            "FETCH NEXT 15 ROWS ONLY" +
            "</script>"})
    @Results(id = "customerListResult", value = {
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "address", column = "address"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number")
    })
    List<CustomerRes> findCustomers(@Param("CustomerPageReq") CustomerPageReq customerPageReq);

    @Select({"<script>" +
            "SELECT count(cu.customer_id) \n" +
            "FROM (\n" +
            "SELECT\n" +
            "customer_id, \n" +
            "customer_name, \n" +
            "address, \n" +
            "email, \n" +
            "phone_number \n" +
            "FROM customer \n" +
            "WHERE \n" +
            "<if test = \"CustomerPageReq.isDeleted==null\" >\n" +
            "is_deleted = 0 \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.isDeleted==1\" >\n" +
            "is_deleted = 1 \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.isDeleted==0\" >\n" +
            "is_deleted = 0 \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.isDeleted!=null\" >\n" +
            "<if test = \"CustomerPageReq.isDeleted!=0 and CustomerPageReq.isDeleted!=1\" >\n" +
            "is_deleted = #{CustomerPageReq.isDeleted} \n" +
            "</if> \n" +
            "</if> \n" +
            "<if test = \"CustomerPageReq.customerName!=null\" > \n" +
            "AND customer_name LIKE N'%${CustomerPageReq.customerName}%'\n" +
            "</if>\n" +
            "<if test = \"CustomerPageReq.address!=null\" > \n" +
            "AND address LIKE N'%${CustomerPageReq.address}%'\n" +
            "</if>\n" +
            "<if test = \"CustomerPageReq.email!=null\" > \n" +
            "AND email LIKE '%${CustomerPageReq.email}%'\n" +
            "</if>\n" +
            "<if test = \"CustomerPageReq.phoneNumber!=null\" > \n" +
            "AND phone_number LIKE N'%${CustomerPageReq.phoneNumber}%'\n" +
            "</if>\n" +
            ") cu" +
            "</script>"})
    int findTotalCustomerWhenFiltering(@Param("CustomerPageReq") CustomerPageReq customerPageReq);

    @Select("SELECT \n" +
            "COUNT(*) \n" +
            "FROM customer \n" +
            "WHERE is_deleted = 0")
    int findTotalCustomer();
}
