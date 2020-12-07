package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.CustomerPageReq;
import com.fa20se28.vma.request.CustomerReq;
import com.fa20se28.vma.response.CustomerDetailRes;
import com.fa20se28.vma.response.CustomerPageRes;
import com.fa20se28.vma.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customer-id}")
    public CustomerDetailRes getCustomerByCustomerId(@PathVariable("customer-id") String customerId) {
        return customerService.findCustomerByCustomerId(customerId);
    }

    @GetMapping
    public CustomerPageRes getCustomers(@RequestParam(required = false) String customerName,
                                        @RequestParam(required = false) String address,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) String phoneNumber,
                                        @RequestParam(required = false) Integer isDeleted,
                                        @RequestParam(required = false, defaultValue = "0") int page) {
        return customerService.getCustomers(new CustomerPageReq(customerName, address, email, phoneNumber, isDeleted, page * 15));
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

    @GetMapping("/count")
    public int getTotalCustomers(@RequestParam(required = false) String customerName,
                                 @RequestParam(required = false) String address,
                                 @RequestParam(required = false) String email,
                                 @RequestParam(required = false) String phoneNumber,
                                 @RequestParam(required = false) Integer isDeleted) {
        return customerService.getTotalCustomers(new CustomerPageReq(customerName, address, email, phoneNumber,isDeleted , 0));
    }
}
