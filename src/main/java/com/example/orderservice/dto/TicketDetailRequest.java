package com.example.orderservice.dto;

import lombok.Data;

@Data
public class TicketDetailRequest {
    private Integer amount;
    private Long productId;
    private Long ticketId;
}