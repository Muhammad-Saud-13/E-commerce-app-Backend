package com.example.ecom.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cart-items")
public class CartItem {

    @Id
    String id;

    String customerId;
    String productId;
    int quantity;

    public CartItem( String customerId, String productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
