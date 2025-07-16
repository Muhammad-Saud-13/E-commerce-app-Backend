package com.example.Ecom.Service;

import com.example.Ecom.Entity.Product;
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
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    public ResponseEntity<String> updateProduct(Product updatedProd, String prodId) {
        Optional<Product> opExistingProduct = productRepo.findById(prodId);

        if (opExistingProduct.isEmpty()) {
            return new ResponseEntity<>("Product not found with id: " + prodId, HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("Product is not present",HttpStatus.NOT_FOUND);
        }
        Product product=optionalProduct.get();
        productRepo.delete(product);
        return new ResponseEntity<>("Deleted:"+product,HttpStatus.OK);
    }
}
