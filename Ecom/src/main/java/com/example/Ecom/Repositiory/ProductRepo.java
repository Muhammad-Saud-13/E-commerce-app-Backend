package com.example.Ecom.Repositiory;

import com.example.Ecom.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {
}
