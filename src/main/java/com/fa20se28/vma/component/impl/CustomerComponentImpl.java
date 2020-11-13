package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.CustomerComponent;
import com.fa20se28.vma.configuration.CustomUtils;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.ContractMapper;
import com.fa20se28.vma.mapper.CustomerMapper;
import com.fa20se28.vma.model.Contract;
import com.fa20se28.vma.model.Customer;
import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerComponentImpl implements CustomerComponent {
    private final CustomerMapper customerMapper;
    private final ContractMapper contractMapper;

    public CustomerComponentImpl(CustomerMapper customerMapper, ContractMapper contractMapper) {
        this.customerMapper = customerMapper;
        this.contractMapper = contractMapper;
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
        List<Contract> contractList = contractMapper.getUnfinishedContractByContractOwnerId(customerId);
        if (contractList.isEmpty()) {
            customerMapper.deleteCustomer(customerId);
        } else {
            String detailMessage = contractList.stream().map(Objects::toString).collect(Collectors.joining(","));
            throw new ResourceIsInUsedException(
                    "Customer with id: " + customerId + " still has unfinished contracts: " + detailMessage);
        }
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

    @Override
    public int findTotalCustomersWhenFiltering(CustomerPageReq customerPageReq) {
        return customerMapper.findTotalCustomerWhenFiltering(customerPageReq);
    }

    @Override
    public int findTotalCustomers() {
        return customerMapper.findTotalCustomer();
    }
}
