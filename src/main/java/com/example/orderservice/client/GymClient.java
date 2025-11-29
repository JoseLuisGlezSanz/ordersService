package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderservice.dto.GymResponse;

@FeignClient(name = "user-microservice", url = "/api/v1/gyms")
public interface GymClient {
    
    @GetMapping("/{id}")
    GymResponse findById(@PathVariable Long id);
    
    @GetMapping("/name/{name}")
    GymResponse findByName(@PathVariable String name);
}