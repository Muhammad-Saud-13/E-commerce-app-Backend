package com.example.ecom.controller;

import com.example.ecom.entity.Customer;
import com.example.ecom.entity.Product;
import com.example.ecom.service.serviceImpl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/add-customer")
    public ResponseEntity<Customer> addNewCustomer(@Valid @RequestBody Customer customer) {
        Customer saved = customerService.save(customer);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }



    @PostMapping("/buy-cart/{customerId}")
    public ResponseEntity<?> buyAllCart(@PathVariable String customerId){
        return customerService.buyCart(customerId);
    }

    @GetMapping("/get-all-purchased/{custId}")
    public ResponseEntity<List<Product>> getAllPurchasedItemsofCustomer(@PathVariable String custId){
        return customerService.getAllItems(custId);
    }

    @PostMapping("/add-to-cart/{custId}/{prodId}/{quantity}")
    public ResponseEntity<?> addItemtoCart(@PathVariable String custId, @PathVariable String prodId, @PathVariable int quantity){
        return customerService.addToCart(custId,prodId,quantity);
    }
}

