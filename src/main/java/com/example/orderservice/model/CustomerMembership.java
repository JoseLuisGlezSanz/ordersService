package com.example.orderservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers_memberships")
@IdClass(CustomerMembershipPk.class)
public class CustomerMembership {
    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "membership_id")
    private Long membershipId;

    @Column(name = "start_date")
    @JsonProperty("fecha de inicio de la membresia")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonProperty("fecha de finalización de la membresia")
    private LocalDate endDate;

    @Column(name = "member_since", nullable = false)
    @JsonProperty("fecha de registro de la membresía")
    private LocalDateTime memberSince;

    @Column(name = "membership_status", nullable = false)
    @JsonProperty("estado de la membresía")
    private Boolean membershipStatus;

    // Relaciones
    @JoinColumn(name = "gym_id")
    private Long gymId;
}