package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderservice.dto.MembershipResponse;
import com.example.orderservice.dto.ProductResponse;

@FeignClient(name = "catalog-microservice", url = "/api/v1")
public interface CatalogClient {
    @GetMapping("/memberships/{id}")
    MembershipResponse findMembershipById(@PathVariable Long id);
    
    @GetMapping("/products/{id}")
    ProductResponse findProductById(@PathVariable Long id);
    
    @GetMapping("/products/price/{id}")
    java.math.BigDecimal getProductPrice(@PathVariable Long id);
}