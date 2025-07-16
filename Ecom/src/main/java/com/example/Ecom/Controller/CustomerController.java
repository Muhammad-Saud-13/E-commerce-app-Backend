package com.example.Ecom.Controller;

import com.example.Ecom.Entity.Customer;
import com.example.Ecom.Entity.Product;
import com.example.Ecom.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;git

    @PostMapping("/add-customer")
    public ResponseEntity<Customer> addNewCustomer(@RequestBody Customer customer) {
        Customer saved = customerService.save(customer);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }



    @PostMapping("/buy/{customerId}/{productId}")
    public ResponseEntity<String> buy(@PathVariable String customerId,@PathVariable String productId){
     return customerService.buyProduct(productId,customerId);
    }

    @GetMapping("/get-all-purchased/{custId}")
    public ResponseEntity<List<Product>> getAllPurchasedItemsofCustomer(@PathVariable String custId){
        return customerService.getAllItems(custId);
    }
}
