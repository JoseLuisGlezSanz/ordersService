package com.example.orderservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderservice.dto.CustomerResponse;

@FeignClient(name = "user-microservice", url = "/api/v1/customers")
public interface CustomerClient {

    @GetMapping("/{id}")
    CustomerResponse findById(Long id);

    @GetMapping("/name/{name}")
    List<CustomerResponse> findByName(@PathVariable String name);
}