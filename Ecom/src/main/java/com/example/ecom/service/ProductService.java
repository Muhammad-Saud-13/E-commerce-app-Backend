package com.example.ecom.service;

import com.example.ecom.entity.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    void saveProduct(Product product);

    ResponseEntity<Product> getById(String id);

    ResponseEntity<String> updateProduct(Product updatedProd, String prodId);

    ResponseEntity<String> removeProduct(String prodId);
}
