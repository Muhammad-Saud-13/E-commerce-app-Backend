package com.example.Ecom.Entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;

    @NonNull
    private String name;
    private String phoneNo;

    private List<String> purchasedProductIds = new ArrayList<>();

}
