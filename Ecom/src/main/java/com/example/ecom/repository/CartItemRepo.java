package com.example.ecom.repository;

import com.example.ecom.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends MongoRepository<CartItem, String> {
    Optional<CartItem> findByCustomerIdAndProductId(String customerId, String productId);
    List<CartItem> findByCustomerId(String customerId);


}
