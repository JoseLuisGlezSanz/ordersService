package com.example.orderservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.orderservice.client.CustomerClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.TicketRequest;
import com.example.orderservice.dto.TicketResponse;
import com.example.orderservice.mapper.TicketMapper;
import com.example.orderservice.model.Ticket;
import com.example.orderservice.repository.TicketRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final CustomerClient customerClient;
    private final UserClient userClient;

    @Override
    public List<TicketResponse> findAll() {
        return ticketRepository.findAll().stream()
                .map(TicketMapper::toResponse)
                .toList();
    }

    @Override
    public TicketResponse findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        return TicketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse create(TicketRequest ticketRequest) {
        var customer = customerClient.findById(ticketRequest.getCustomerId());
        if (customer == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + ticketRequest.getCustomerId());
        }

        var user = userClient.findById(ticketRequest.getUserId());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado con ID: " + ticketRequest.getUserId());
        }
        
        Ticket ticket = TicketMapper.toEntity(ticketRequest);
        
        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketMapper.toResponse(savedTicket);
    }

    @Override
    public TicketResponse update(Long id, TicketRequest ticketRequest) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));

        var customer = customerClient.findById(ticketRequest.getCustomerId());
        if (customer == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + ticketRequest.getCustomerId());
        }

        var user = userClient.findById(ticketRequest.getUserId());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado con ID: " + ticketRequest.getUserId());
        }

        TicketMapper.copyToEntity(ticketRequest, existingTicket);

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return TicketMapper.toResponse(updatedTicket);
    }

    // @Override
    // public void delete(Integer id) {
    //     ticketRepository.deleteById(id);
    // }

    @Override
    public List<TicketResponse> getAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Ticket> tickets = ticketRepository.findAll(pageReq);
        return tickets.getContent().stream().map(TicketMapper::toResponse).toList();
    }

    @Override
    public List<TicketResponse> findAllTicketsByCustomerId(Long customerId) {
        List<Ticket> tickets = ticketRepository.findAllTicketsByCustomerId(customerId);
        return tickets.stream().map(TicketMapper::toResponse).toList();
    }

    @Override
    public List<TicketResponse> getAllTicketsByCustomerId(int page, int pageSize, Long customerId) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Ticket> tickets = ticketRepository.getAllTicketsByCustomerId(customerId, pageReq);
        return tickets.getContent().stream().map(TicketMapper::toResponse).toList();
    }

    @Override
    public List<TicketResponse> findAllTicketsByUserId(Long userId) {
        List<Ticket> tickets = ticketRepository.findAllTicketsByUserId(userId);
        return tickets.stream().map(TicketMapper::toResponse).toList();
    }

    @Override
    public List<TicketResponse> getAllTicketsByUserId(int page, int pageSize, Long userId) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Ticket> tickets = ticketRepository.getAllTicketsByUserId(userId, pageReq);
        return tickets.getContent().stream().map(TicketMapper::toResponse).toList();
    }
}