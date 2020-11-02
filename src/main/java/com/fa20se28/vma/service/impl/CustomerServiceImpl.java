package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.CustomerComponent;
import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerDetailRes;
import com.fa20se28.vma.response.CustomerPageRes;
import com.fa20se28.vma.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerComponent customerComponent;

    public CustomerServiceImpl(CustomerComponent customerComponent) {
        this.customerComponent = customerComponent;
    }

    @Override
    public int createCustomer(CustomerReq customerReq) {
        return customerComponent.insertCustomer(customerReq);
    }

    @Override
    public void updateCustomer(CustomerReq customerReq) {
        customerComponent.updateCustomer(customerReq);
    }

    @Override
    public CustomerPageRes getDrivers(CustomerPageReq customerPageReq) {
        CustomerPageRes customerPageRes = new CustomerPageRes();
        customerPageRes.setCustomerRes(customerComponent.findCustomers(customerPageReq));
        return customerPageRes;
    }

    @Override
    public CustomerDetailRes findCustomerByCustomerId(String customerId) {
        CustomerDetailRes customerDetailRes = new CustomerDetailRes();
        customerDetailRes.setCustomer(customerComponent.findCustomerByCustomerId(customerId));
        return customerDetailRes;
    }

    @Override
    public int getTotalCustomers(CustomerPageReq customerPageReq) {
        if (customerPageReq.getCustomerName() != null
                || customerPageReq.getAddress() != null
                || customerPageReq.getEmail() != null
                || customerPageReq.getPhoneNumber() != null) {
            return customerComponent.findTotalCustomersWhenFiltering(customerPageReq);
        }
        return customerComponent.findTotalCustomers();
    }

    @Override
    public void deleteCustomerByCustomerId(String customerId) {
        customerComponent.deleteCustomer(customerId);
    }
}
