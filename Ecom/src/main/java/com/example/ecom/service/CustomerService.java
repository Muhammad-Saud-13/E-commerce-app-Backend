package com.example.ecom.service;

import com.example.ecom.entity.Customer;
import com.example.ecom.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    ResponseEntity<?> buyCart(String customerId);
    ResponseEntity<List<Product>> getAllItems(String customerId);
    ResponseEntity<?> addToCart(String customerId, String productId, int quantity);
}
