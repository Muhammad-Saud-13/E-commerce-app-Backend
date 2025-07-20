package com.example.ecom.controller;

import com.example.ecom.entity.Product;
import com.example.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Void> addProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        return productService.getById(id);
    }

    @PutMapping("/update/{prodId}")
    public ResponseEntity<String> updateProduct(@RequestBody Product product, @PathVariable String prodId){
        return productService.updateProduct(product,prodId);
    }

    @DeleteMapping("/removeProduct/{prodId}")
    public ResponseEntity<String> removeProduct(@PathVariable String prodId){
        return productService.removeProduct(prodId);
    }

}
