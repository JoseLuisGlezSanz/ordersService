package com.example.orderservice.mapper;

import java.time.LocalDateTime;

import com.example.orderservice.dto.TicketRequest;
import com.example.orderservice.dto.TicketResponse;
import com.example.orderservice.model.Ticket;

public class TicketMapper {
    public static TicketResponse toResponse(Ticket ticket) {
        if (ticket == null) return null;
        
        return TicketResponse.builder()
                .id(ticket.getId())
                .date(ticket.getDate())
                .total(ticket.getTotal())
                .status(ticket.getStatus())
                .saleDate(ticket.getSaleDate())
                .methodPayment(ticket.getMethodPayment())
                .paymentWith(ticket.getPaymentWith())
                .customerId(ticket.getCustomerId())
                .userId(ticket.getUserId())
                .build();
    }

    public static Ticket toEntity(TicketRequest ticketRequest) {
        if (ticketRequest == null) return null;
        
        return Ticket.builder()
                .date(LocalDateTime.now())
                .total(ticketRequest.getTotal())
                .status(ticketRequest.getStatus())
                .saleDate(LocalDateTime.now())
                .methodPayment(ticketRequest.getMethodPayment())
                .paymentWith(ticketRequest.getPaymentWith())
                .customerId(ticketRequest.getCustomerId())
                .userId(ticketRequest.getUserId())
                .build();
    }

    public static void copyToEntity(TicketRequest ticketRequest, Ticket entity) {
        if (ticketRequest == null || entity == null) return;
        
        entity.setTotal(ticketRequest.getTotal());
        entity.setStatus(ticketRequest.getStatus());
        entity.setMethodPayment(ticketRequest.getMethodPayment());
        entity.setPaymentWith(ticketRequest.getPaymentWith());
        entity.setCustomerId(ticketRequest.getCustomerId());
        entity.setUserId(ticketRequest.getUserId());
    }
}