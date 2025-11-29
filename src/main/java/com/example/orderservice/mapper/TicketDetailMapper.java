package com.example.orderservice.mapper;

import java.math.BigDecimal;

import com.example.orderservice.dto.TicketDetailRequest;
import com.example.orderservice.dto.TicketDetailResponse;
import com.example.orderservice.model.Ticket;
import com.example.orderservice.model.TicketDetail;

public class TicketDetailMapper {
    public static TicketDetailResponse toResponse(TicketDetail ticketDetail) {
        if (ticketDetail == null) return null;
        
        return TicketDetailResponse.builder()
                .id(ticketDetail.getId())
                .amount(ticketDetail.getAmount())
                .unitPrice(ticketDetail.getUnitPrice())
                .subtotal(ticketDetail.getSubtotal())
                .productId(ticketDetail.getProductId())
                .ticketId(ticketDetail.getTicket().getId())
                .build();
    }

    public static TicketDetail toEntity(TicketDetailRequest ticketDetailRequest, BigDecimal productPrice, Ticket ticket) {
        if (ticketDetailRequest == null) return null;
        
        return TicketDetail.builder()
                .amount(ticketDetailRequest.getAmount())
                .unitPrice(productPrice)
                .subtotal(productPrice.multiply(BigDecimal.valueOf(ticketDetailRequest.getAmount())))
                .productId(ticketDetailRequest.getProductId())
                .ticket(ticket)
                .build();
    }

    public static void copyToEntity(TicketDetailRequest ticketDetailRequest, TicketDetail entity, BigDecimal productPrice, Ticket ticket) {
        if (ticketDetailRequest == null || entity == null) return;
        
        entity.setAmount(ticketDetailRequest.getAmount());
        entity.setUnitPrice(productPrice);
        entity.setSubtotal(productPrice.multiply(BigDecimal.valueOf(ticketDetailRequest.getAmount())));
        entity.setProductId(ticketDetailRequest.getProductId());
        entity.setTicket(ticket);
    }
}