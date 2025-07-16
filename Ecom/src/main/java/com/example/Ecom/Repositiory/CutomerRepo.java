package com.example.Ecom.Repositiory;

import com.example.Ecom.Entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CutomerRepo extends MongoRepository<Customer, String> {
}
