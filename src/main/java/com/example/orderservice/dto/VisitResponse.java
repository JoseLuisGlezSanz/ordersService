package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponse {
    @JsonProperty("Visit identifier")
    private Long id;

    private Long customerId;
    private LocalDateTime date;
    private Boolean pending;
    private Long gymId;
}