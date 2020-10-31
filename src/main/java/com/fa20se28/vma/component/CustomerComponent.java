package com.fa20se28.vma.component;

import com.fa20se28.vma.request.CustomerReq;

public interface CustomerComponent {
    int insertCustomer(CustomerReq customerReq);

    void updateCustomer(CustomerReq customerReq);

    void deleteCustomer(String customerId);
}
