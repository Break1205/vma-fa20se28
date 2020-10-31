package com.fa20se28.vma.service;

import com.fa20se28.vma.request.CustomerReq;

public interface CustomerService {
    int createCustomer(CustomerReq customerReq);

    void updateCustomer(CustomerReq customerReq);

    void deleteCustomerByCustomerId(String customerId);
}
