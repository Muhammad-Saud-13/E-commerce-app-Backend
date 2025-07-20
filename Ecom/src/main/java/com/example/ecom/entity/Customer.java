package com.example.ecom.entity;
import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;

    @NotBlank(message = "name must not be null")
    private String name;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^(03)[0-9]{9}$", message = "Phone number must start with 03 and be 11 digits")
    @Size(min = 11, max = 11, message = "Phone number must be exactly 11 digits")
    private String phoneNo;


    private List<String> purchasedProductIds = new ArrayList<>();

    public Customer(){

    }

}
