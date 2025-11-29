package com.example.orderservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.orderservice.client.CatalogClient;
import com.example.orderservice.dto.TicketDetailRequest;
import com.example.orderservice.dto.TicketDetailResponse;
import com.example.orderservice.mapper.TicketDetailMapper;
import com.example.orderservice.model.Ticket;
import com.example.orderservice.model.TicketDetail;
import com.example.orderservice.repository.TicketDetailRepository;
import com.example.orderservice.repository.TicketRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketDetailServiceImpl implements TicketDetailService{
    private final TicketDetailRepository ticketDetailRepository;
    private final CatalogClient catalogClient;
    private final TicketRepository ticketRepository;

    @Override
    public List<TicketDetailResponse> findAll() {
        return ticketDetailRepository.findAll().stream()
                .map(TicketDetailMapper::toResponse)
                .toList();
    }

    @Override
    public TicketDetailResponse findById(Long id) {
        TicketDetail ticketDetail = ticketDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de ticket no encontrado con ID: " + id));

        return TicketDetailMapper.toResponse(ticketDetail);
    }

    @Override
    public TicketDetailResponse create(TicketDetailRequest ticketDetailRequest) {
        BigDecimal productPrice = catalogClient.getProductPrice(ticketDetailRequest.getProductId());

        Ticket ticket = ticketRepository.findById(ticketDetailRequest.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketDetailRequest.getTicketId()));
        
        TicketDetail ticketDetail = TicketDetailMapper.toEntity(ticketDetailRequest, productPrice, ticket);
        
        TicketDetail savedTicketDetail = ticketDetailRepository.save(ticketDetail);
        return TicketDetailMapper.toResponse(savedTicketDetail);
    }

    @Override
    public TicketDetailResponse update(Long id, TicketDetailRequest ticketDetailRequest) {
        TicketDetail existingTicketDetail = ticketDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de ticket no encontrado con ID: " + id));

        BigDecimal productPrice = catalogClient.getProductPrice(ticketDetailRequest.getProductId());

        Ticket ticket = ticketRepository.findById(ticketDetailRequest.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketDetailRequest.getTicketId()));
        
        TicketDetailMapper.copyToEntity(ticketDetailRequest, existingTicketDetail, productPrice, ticket);

        TicketDetail updatedTicketDetail = ticketDetailRepository.save(existingTicketDetail);
        return TicketDetailMapper.toResponse(updatedTicketDetail);
    }

    // @Override
    // public void delete(Integer id) {
    //     ticketDetailRepository.deleteById(id);
    // }

    public List<TicketDetailResponse> getAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<TicketDetail> ticketsDetails = ticketDetailRepository.findAll(pageReq);
        return ticketsDetails.getContent().stream().map(TicketDetailMapper::toResponse).toList();
    }

    @Override
    public List<TicketDetailResponse> findByTicketId(Long ticketId) {
        List<TicketDetail> ticketDetailResponses = ticketDetailRepository.findByTicketId(ticketId);
        return ticketDetailResponses.stream().map(TicketDetailMapper::toResponse).toList();
    }
}