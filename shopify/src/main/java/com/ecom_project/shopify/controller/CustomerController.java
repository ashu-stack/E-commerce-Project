package com.ecom_project.shopify.controller;

import com.ecom_project.shopify.dto.CustomerDTO;
import com.ecom_project.shopify.dto.Mapper;
import com.ecom_project.shopify.model.Customer;
import com.ecom_project.shopify.repository.CustomerRepo;
import com.ecom_project.shopify.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    Mapper mapper;

    //getAll
    @GetMapping("admin/customer/getAll")
    public ResponseEntity<List<CustomerDTO>> getCustomers(){
        List<CustomerDTO> dtoList = customerService.getAllCustomers();


        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    //get by id
    @GetMapping("admin/customer/getById/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id){
        CustomerDTO customerDTO = customerService.getCustomerById(id);


        if(customerDTO != null){
            return new ResponseEntity<>(customerDTO,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // put
    @PostMapping("user/customer/addCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // delete
    @DeleteMapping("admin/customer/deleteById/{id}")
    public ResponseEntity deleteCustomer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
        return new ResponseEntity(HttpStatus.GONE);
    }

}
