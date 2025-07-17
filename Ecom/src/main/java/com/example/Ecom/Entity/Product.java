package com.example.Ecom.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="products")
public class Product {

    @Id
    private String id;

    @NonNull
    private String name;
    private String description;

    @NonNull
    private double price;

    @NonNull
    private int stock;

}
