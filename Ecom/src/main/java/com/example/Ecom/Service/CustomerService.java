package com.example.Ecom.Service;

import com.example.Ecom.Entity.Customer;
import com.example.Ecom.Entity.Product;
import com.example.Ecom.Exception.CustomerNotFoundException;
import com.example.Ecom.Exception.ProductNotFoundException;
import com.example.Ecom.Exception.ProductOutOfStockException;
import com.example.Ecom.Repositiory.CustomerRepo;
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
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;

    public Customer save(Customer customer){
        customerRepo.save(customer);
        return customer;
    }

    public ResponseEntity<String> buyProduct(String prodId, String customerId) {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id"+customerId+" not found!");
        }

        Optional<Product> productOptional = productRepo.findById(prodId);
        if (productOptional.isEmpty()) {
            throw  new ProductNotFoundException("Product with id "+prodId+" not found");
        }

        Customer customer = customerOptional.get();
        Product product = productOptional.get();

        if (product.getStock() <= 0) {
            throw new ProductOutOfStockException("Product with id "+prodId+" is out of stock!");
        }

        customer.getPurchasedProductIds().add(prodId);
        product.setStock(product.getStock() - 1);

        customerRepo.save(customer);
        productRepo.save(product);

        return new ResponseEntity<>("Purchased!", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<Product>> getAllItems(String id){
        Optional<Customer> optionalCustomer=customerRepo.findById(id);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer with id"+id+" not found!");
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

