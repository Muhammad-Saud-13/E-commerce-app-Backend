package com.example.ecom.service.serviceImpl;

import com.example.ecom.entity.Product;
import com.example.ecom.exception.ProductNotFoundException;
import com.example.ecom.repository.ProductRepo;
import com.example.ecom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void saveProduct(Product product){
        log.debug("Saving product: {}", product);
        productRepo.save(product);
        log.info("Product saved with ID: {}", product.getId());
    }

    @Override
    public ResponseEntity<Product> getById(String id) {
        log.debug("Requested to fetch product with ID: {}", id);

        Optional<Product> product = productRepo.findById(id);

        if (product.isPresent()) {
            log.info("Product found with ID: {} -> {}", id, product.get());
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            log.warn("Product not found with ID: {}", id);
            throw new ProductNotFoundException("Product Not found with id " + id);
        }
    }
    @Override
    public ResponseEntity<String> updateProduct(Product updatedProd, String prodId) {
        Optional<Product> opExistingProduct = productRepo.findById(prodId);

        if (opExistingProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with "+prodId+" not found!");
        }

        Product existingProduct = opExistingProduct.get();

        // Only update fields that are not null
        if (updatedProd.getName() != null) {
            existingProduct.setName(updatedProd.getName());
        }
        if (updatedProd.getDescription() != null) {
            existingProduct.setDescription(updatedProd.getDescription());
        }
        if (updatedProd.getPrice() != 0.0) {
            existingProduct.setPrice(updatedProd.getPrice());
        }
        if (updatedProd.getStock() != 0) {
            existingProduct.setStock(updatedProd.getStock());
        }

        productRepo.save(existingProduct);

        return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> removeProduct(String prodId) {
        Optional<Product> optionalProduct=productRepo.findById(prodId);
        if(!optionalProduct.isPresent()){
            throw new ProductNotFoundException("Product with "+prodId+" not found");
        }
        Product product=optionalProduct.get();
        productRepo.delete(product);
        return new ResponseEntity<>("Deleted:"+product,HttpStatus.OK);
    }
}
