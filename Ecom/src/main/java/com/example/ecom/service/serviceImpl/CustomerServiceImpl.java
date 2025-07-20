package com.example.ecom.service.serviceImpl;

import com.example.ecom.controller.DiscountClient;
import com.example.ecom.entity.CartItem;
import com.example.ecom.entity.Customer;
import com.example.ecom.entity.Product;
import com.example.ecom.exception.CustomerNotFoundException;
import com.example.ecom.exception.ProductNotFoundException;
import com.example.ecom.repository.CartItemRepo;
import com.example.ecom.repository.CustomerRepo;
import com.example.ecom.repository.ProductRepo;
import com.example.ecom.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private DiscountClient discountClient;
    @Autowired
    private CartItemRepo cartItemRepo;


    @Override
    public Customer save(Customer customer){
        customerRepo.save(customer);
        log.info("New customer saved: {}", customer.getId());
        return customer;
    }


    @Transactional
    @Override
    public ResponseEntity<String> buyCart(String customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " not found!"));

        List<CartItem> cartItems = cartItemRepo.findByCustomerId(customerId);

        if (cartItems.isEmpty()) {
            log.info("Customer {} tried to buy but cart is empty.", customerId);
            return new ResponseEntity<>("No products in cart.\nPlease add products before purchasing.", HttpStatus.NOT_FOUND);
        }

        List<Product> unavailableProducts = new ArrayList<>();
        List<CartItem> filtered=cartItems.stream().filter(item->{
            Optional<Product> productOptional=productRepo.findById(item.getProductId());
            boolean available=productOptional.isPresent() && productOptional.get().getStock()>=item.getQuantity();

            if(!available){
                cartItemRepo.delete(item);
                productOptional.ifPresent(unavailableProducts::add);
                log.warn("Product {} removed from cart due to unavailability.", item.getProductId());
            }
            return available;
        }).toList();
        cartItems=filtered;


        if (cartItems.isEmpty()) {
            log.warn("All products were unavailable for customer {}", customerId);
            return new ResponseEntity<>("All products in your cart are out of stock.", HttpStatus.NOT_FOUND);
        }

        double totalPrice = 0.0;

        for (CartItem item : cartItems) {
            Optional<Product> productOptional = productRepo.findById(item.getProductId());
            Product product = productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + item.getProductId()));

            // Add to purchased list
            customer.getPurchasedProductIds().add(product.getId());

            // Update stock
            int newStock = product.getStock() -item.getQuantity();
            product.setStock(newStock);
            productRepo.save(product);
            log.info("Customer {} purchased product {}. New stock: {}", customerId, product.getId(), newStock);

            // Add to total price
            totalPrice += product.getPrice();
        }

        customerRepo.save(customer);
        log.info("Customer {} purchase record updated.", customerId);


        // Calculate discount
        log.info("Discount request send Discount service for customer {}",customerId);
        double discountRate = discountClient.calculateDiscount(cartItems.size());
        double discountAmount = totalPrice * discountRate;
        double finalPrice = totalPrice - discountAmount;

        // Clear cart
        cartItemRepo.deleteAll(cartItems);
        log.info("Cart cleared for customer {} after successful purchase.", customerId);

        String msg = String.format(
                " Purchase successful!\nTotal Bill: %.2f\nDiscount: %.0f%%\nFinal Price: %.2f",
                totalPrice, discountRate * 100, finalPrice
        );
        log.info("Customer {} - Bill: {}, Discount: {}%, Final: {}", customerId, totalPrice, discountRate * 100, finalPrice);
        return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<Product>> getAllItems(String id){
        Optional<Customer> optionalCustomer=customerRepo.findById(id);
        if(!optionalCustomer.isPresent()){
            log.warn("Customer {} not found when trying to fetch purchase history.", id);
            throw new CustomerNotFoundException("Customer with id"+id+" not found!");
        }
        Customer customer=optionalCustomer.get();
        List<String> prodIds=customer.getPurchasedProductIds();

        if(prodIds.isEmpty()){
            log.info("Customer {} has no purchased items.", id);
            return new ResponseEntity<>(Collections.EMPTY_LIST,HttpStatus.OK);
        }
        List<Product> purchasedProducts=productRepo.findAllById(prodIds);
        log.info("Fetched {} purchased products for customer {}", purchasedProducts.size(), id);
        return new ResponseEntity<>(purchasedProducts,HttpStatus.FOUND);
    }


    @Override
    public ResponseEntity<?> addToCart(String custId, String prodId, int quantity) {
        Optional<Customer> customerOptional=customerRepo.findById(custId);
        if(!customerOptional.isPresent()){
            log.warn("Customer {} not found when adding to cart.", custId);
            throw new CustomerNotFoundException("Customer not found with id "+custId);
        }
        Optional<Product> productOptional = productRepo.findById(prodId);
        if (productOptional.isEmpty()) {
            log.warn("Product {} not found when adding to cart for customer {}", prodId, custId);
            throw  new ProductNotFoundException("Product with id "+prodId+" not found");
        }

        Customer customer = customerOptional.get();
        Product product = productOptional.get();

        Optional<CartItem> optionalCartItemcartItem=cartItemRepo.findByCustomerIdAndProductId(custId,prodId);
        if(!optionalCartItemcartItem.isPresent()){
            CartItem cartItem=new CartItem(custId,prodId,quantity);
            cartItemRepo.save(cartItem);
            log.info("Product {} added to cart for customer {} with quantity {}", prodId, custId, quantity);
            return new ResponseEntity<>("Added to Cart",HttpStatus.ACCEPTED);
        }
        else{
            CartItem existingCartItem=optionalCartItemcartItem.get();
            existingCartItem.setQuantity(quantity);
            cartItemRepo.save(existingCartItem);
            log.info("Cart item updated for customer {} and product {} with new quantity {}", custId, prodId, quantity);
            return new ResponseEntity<>("Product is already in cart \n Quantity updated to "+quantity,HttpStatus.ACCEPTED);
        }



    }
}

