package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderservice.dto.MembershipResponse;

@FeignClient(name = "catalog-microservice", url = "/api/v1/memberships")
public interface MembershipClient {
    @GetMapping("/memberships/{id}")
    MembershipResponse findMembershipById(@PathVariable Long id);
}
