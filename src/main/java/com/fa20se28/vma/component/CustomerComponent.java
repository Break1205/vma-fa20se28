package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Customer;
import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerRes;

import java.util.List;

public interface CustomerComponent {
    int insertCustomer(CustomerReq customerReq);

    void updateCustomer(CustomerReq customerReq);

    void deleteCustomer(String customerId);

    List<CustomerRes> findCustomers(CustomerPageReq customerPageReq);

    Customer findCustomerByCustomerId(String customerId);
}
