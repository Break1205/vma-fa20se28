package com.fa20se28.vma.service;

import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerDetailRes;
import com.fa20se28.vma.response.CustomerPageRes;

public interface CustomerService {
    int createCustomer(CustomerReq customerReq);

    void updateCustomer(CustomerReq customerReq);

    void deleteCustomerByCustomerId(String customerId);

    CustomerPageRes getDrivers(CustomerPageReq customerPageReq);

    CustomerDetailRes findCustomerByCustomerId(String customerId);
}
