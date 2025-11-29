package com.example.orderservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMembershipResponse {
    @JsonProperty("Customer identifier:")
    private Long customerId;

    private Long membershipId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime memberSince;
    private Boolean membershipStatus;
    private Long gymId;
}