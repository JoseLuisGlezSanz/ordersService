package com.example.orderservice.mapper;

import java.time.LocalDateTime;

import com.example.orderservice.dto.VisitRequest;
import com.example.orderservice.dto.VisitResponse;
import com.example.orderservice.model.Visit;

public class VisitMapper {
    public static VisitResponse toResponse(Visit visit) {
        if (visit == null) return null;
        
        return VisitResponse.builder()
                .id(visit.getId())
                .date(visit.getDate())
                .pending(visit.getPending())
                .customerId(visit.getCustomerId())
                .gymId(visit.getGymId())
                .build();
    }

    public static Visit toEntity(VisitRequest visitRequest) {
        if (visitRequest == null) return null;
        
        return Visit.builder()
                .date(LocalDateTime.now())
                .pending(visitRequest.getPending())
                .customerId(visitRequest.getCustomerId())
                .gymId(visitRequest.getGymId())
                .build();
    }

    public static void copyToEntity(VisitRequest visitRequest, Visit entity) {
        if (visitRequest == null || entity == null) return;
        
        entity.setPending(visitRequest.getPending());
        entity.setCustomerId(visitRequest.getCustomerId());
        entity.setGymId(visitRequest.getGymId());
    }
}