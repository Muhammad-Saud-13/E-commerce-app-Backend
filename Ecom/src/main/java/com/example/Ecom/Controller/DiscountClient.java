package com.example.Ecom.Controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "discount-service", url = "http://localhost:8082/api/discount")
public interface DiscountClient {

    @PostMapping
    Double calculateDiscount(@RequestBody List<String> items);
}
