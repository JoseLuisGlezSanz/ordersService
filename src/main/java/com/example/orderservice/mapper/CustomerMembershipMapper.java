package com.example.orderservice.mapper;

import java.time.LocalDate;

import com.example.orderservice.dto.CustomerMembershipRequest;
import com.example.orderservice.dto.CustomerMembershipResponse;
import com.example.orderservice.model.CustomerMembership;

public class CustomerMembershipMapper {
    public static CustomerMembershipResponse toResponse(CustomerMembership customerMembership) {
        if (customerMembership == null) return null;

        return CustomerMembershipResponse.builder()
                .customerId(customerMembership.getCustomerId())
                .membershipId(customerMembership.getMembershipId())
                .startDate(customerMembership.getStartDate())
                .endDate(customerMembership.getEndDate())
                .memberSince(customerMembership.getMemberSince())
                .membershipStatus(customerMembership.getMembershipStatus())
                .gymId(customerMembership.getGymId())
                .build();
    }

    public static CustomerMembership toEntity(CustomerMembershipRequest customerMembershipRequest) {
        if (customerMembershipRequest == null) return null;

        return CustomerMembership.builder()
                .customerId(customerMembershipRequest.getCustomerId())
                .membershipId(customerMembershipRequest.getMembershipId())
                .startDate(LocalDate.now())
                .endDate(customerMembershipRequest.getEndDate())
                .memberSince(customerMembershipRequest.getMemberSince())
                .membershipStatus(customerMembershipRequest.getMembershipStatus())
                .customerId(customerMembershipRequest.getCustomerId())
                .membershipId(customerMembershipRequest.getMembershipId())
                .gymId(customerMembershipRequest.getGymId())
                .build();
    }

    public static void copyToEntity(CustomerMembershipRequest customerMembershipRequest, CustomerMembership entity) {
        if (customerMembershipRequest == null || entity == null) return;
        
        entity.setEndDate(customerMembershipRequest.getEndDate());
        entity.setMemberSince(customerMembershipRequest.getMemberSince());
        entity.setMembershipStatus(customerMembershipRequest.getMembershipStatus());
        entity.setCustomerId(customerMembershipRequest.getCustomerId());
        entity.setMembershipId(customerMembershipRequest.getMembershipId());
        entity.setGymId(customerMembershipRequest.getGymId());
    }
}