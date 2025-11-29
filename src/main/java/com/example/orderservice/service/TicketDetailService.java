package com.example.orderservice.service;

import java.util.List;

import com.example.orderservice.dto.TicketDetailRequest;
import com.example.orderservice.dto.TicketDetailResponse;

public interface TicketDetailService {
    List<TicketDetailResponse> findAll();
    TicketDetailResponse findById(Long id);
    TicketDetailResponse create(TicketDetailRequest ticketDetailRequest);
    TicketDetailResponse update(Long id, TicketDetailRequest ticketDetailRequest);
    // void delete(Integer id);
    List<TicketDetailResponse> getAll(int page, int pageSize);
    List<TicketDetailResponse> findByTicketId(Long ticketId);
}