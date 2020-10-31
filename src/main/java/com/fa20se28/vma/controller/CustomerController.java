package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createCustomer(@RequestBody CustomerReq customerReq) {
        return customerService.createCustomer(customerReq);
    }

    @PutMapping
    public void updateCustomer(@RequestBody CustomerReq customerReq) {
        customerService.updateCustomer(customerReq);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(String customerId) {
        customerService.deleteCustomerByCustomerId(customerId);
    }
}
