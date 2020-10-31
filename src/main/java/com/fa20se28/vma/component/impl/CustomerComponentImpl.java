package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.CustomerComponent;
import com.fa20se28.vma.configuration.CustomUtils;
import com.fa20se28.vma.mapper.CustomerMapper;
import com.fa20se28.vma.model.Customer;
import com.fa20se28.vma.request.CustomerReq;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerComponentImpl implements CustomerComponent {
    private final CustomerMapper customerMapper;

    public CustomerComponentImpl(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public int insertCustomer(CustomerReq customerReq) {
        String generateId = CustomUtils.randomId();
        Optional<Customer> optionalCustomer = customerMapper.findCustomerByCustomerId(generateId);
        while (optionalCustomer.isPresent()) {
            generateId = CustomUtils.randomId();
            optionalCustomer = customerMapper.findCustomerByCustomerId(generateId);
        }
        return customerMapper.insertCustomer(customerReq);
    }

    @Override
    public void updateCustomer(CustomerReq customerReq) {
        customerMapper.updateCustomer(customerReq);
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerMapper.deleteCustomer(customerId);
    }
}
