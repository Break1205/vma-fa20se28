package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.CustomerComponent;
import com.fa20se28.vma.configuration.CustomUtils;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.CustomerMapper;
import com.fa20se28.vma.model.Customer;
import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerRes;
import org.springframework.stereotype.Component;

import java.util.List;
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
        customerReq.setCustomerId(generateId);
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

    @Override
    public List<CustomerRes> findCustomers(CustomerPageReq customerPageReq) {
        return customerMapper.findCustomers(customerPageReq);
    }

    @Override
    public Customer findCustomerByCustomerId(String customerId) {
        Optional<Customer> optionalCustomer = customerMapper.findCustomerByCustomerId(customerId);
        return optionalCustomer.orElseThrow(() ->
                new ResourceNotFoundException("Customer with id: " + customerId + " not found"));
    }
}
