package com.example.Ecom.Service;

import com.example.Ecom.Entity.Customer;
import com.example.Ecom.Entity.Product;
import com.example.Ecom.Repositiory.CutomerRepo;
import com.example.Ecom.Repositiory.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CutomerRepo cutomerRepo;
    @Autowired
    private ProductRepo productRepo;

    public Customer save(Customer customer){
        cutomerRepo.save(customer);
        return customer;
    }

    public ResponseEntity<String> buyProduct(String prodId, String customerId) {
        Optional<Customer> customerOptional = cutomerRepo.findById(customerId);
        if (customerOptional.isEmpty()) {
            return new ResponseEntity<>("Customer not found!", HttpStatus.NOT_FOUND);
        }

        Optional<Product> productOptional = productRepo.findById(prodId);
        if (productOptional.isEmpty()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        Customer customer = customerOptional.get();
        Product product = productOptional.get();

        if (product.getStock() <= 0) {
            return new ResponseEntity<>("Out of Stock!", HttpStatus.BAD_REQUEST);
        }

        customer.getPurchasedProductIds().add(prodId);
        product.setStock(product.getStock() - 1);

        cutomerRepo.save(customer);
        productRepo.save(product);

        return new ResponseEntity<>("Purchased!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<Product>> getAllItems(String id){
        Optional<Customer> optionalCustomer=cutomerRepo.findById(id);
        if(!optionalCustomer.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer=optionalCustomer.get();
        List<String> prodIds=customer.getPurchasedProductIds();

        if(prodIds.isEmpty()){
            return new ResponseEntity<>(Collections.EMPTY_LIST,HttpStatus.OK);
        }
        List<Product> purchasedProducts=productRepo.findAllById(prodIds);
        return new ResponseEntity<>(purchasedProducts,HttpStatus.FOUND);
    }
}

