package com.example.Ecom.Service;

import com.example.Ecom.Entity.Product;
import com.example.Ecom.Exception.ProductNotFoundException;
import com.example.Ecom.Repositiory.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public void saveProduct(Product product){
        productRepo.save(product);
    }

    public ResponseEntity<Product> getById(String id) {
        Optional<Product> product = productRepo.findById(id);
        return product.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() ->{
                    throw new ProductNotFoundException("Product Not found with id "+id);
                });
    }
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
