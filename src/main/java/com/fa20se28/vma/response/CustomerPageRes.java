package com.fa20se28.vma.response;

import java.util.List;

public class CustomerPageRes {
    private List<CustomerRes> customerRes;

    public List<CustomerRes> getCustomerRes() {
        return customerRes;
    }

    public void setCustomerRes(List<CustomerRes> customerRes) {
        this.customerRes = customerRes;
    }
}
