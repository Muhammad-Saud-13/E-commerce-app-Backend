package com.example.ecom.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "discount-service", url = "http://localhost:8082/api/discount")
public interface DiscountClient {

    @PostMapping
    Double calculateDiscount(@RequestBody int n);
}
