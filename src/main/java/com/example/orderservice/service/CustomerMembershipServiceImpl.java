package com.example.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orderservice.client.MembershipClient;
import com.example.orderservice.client.CustomerClient;
import com.example.orderservice.client.GymClient;
import com.example.orderservice.dto.CustomerMembershipRequest;
import com.example.orderservice.dto.CustomerMembershipResponse;
import com.example.orderservice.mapper.CustomerMembershipMapper;
import com.example.orderservice.model.CustomerMembership;
import com.example.orderservice.model.CustomerMembershipPk;
import com.example.orderservice.repository.CustomerMembershipRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerMembershipServiceImpl implements CustomerMembershipService{
    private final CustomerMembershipRepository customerMembershipRepository;
    private final CustomerClient customerClient;
    private final MembershipClient membershipClient;
    private final GymClient gymClient;

    @Override
    public List<CustomerMembershipResponse> findAll() {
        return customerMembershipRepository.findAll().stream()
                .map(CustomerMembershipMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerMembershipResponse findById(Long customerId, Long membershipId) {
        CustomerMembershipPk id = new CustomerMembershipPk(customerId, membershipId);
        CustomerMembership customerMembership = customerMembershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membresía de cliente no encontrada con IDs: " + customerId + ", " + membershipId));

        return CustomerMembershipMapper.toResponse(customerMembership);
    }

    @Override
    public CustomerMembershipResponse create(CustomerMembershipRequest customerMembershipRequest) {
        // Valida Customer
        var customer = customerClient.findById(customerMembershipRequest.getCustomerId());

        // Valida Membership
        var membership = membershipClient.findMembershipById(customerMembershipRequest.getMembershipId());

        // Validar que el cliente y la membresía pertenezcan al mismo gym
        validateSameGym(customer.getGymId(), membership.getGymId());

        // Validar que el gym existe
        var gym = gymClient.findById(customer.getGymId());
        if (gym == null) {
            throw new RuntimeException("Gym no encontrado con ID: " + customer.getGymId());
        }
        
        CustomerMembership customerMembership = CustomerMembershipMapper.toEntity(customerMembershipRequest);
        
        CustomerMembership savedCustomerMembership = customerMembershipRepository.save(customerMembership);
        return CustomerMembershipMapper.toResponse(savedCustomerMembership);
    }

    @Override
    public CustomerMembershipResponse update(Long customerId, Long membershipId, CustomerMembershipRequest customerMembershipRequest) {
        CustomerMembershipPk id = new CustomerMembershipPk(customerId, membershipId);

        CustomerMembership existingCustomerMembership = customerMembershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membresía de cliente no encontrada"));

        // Si la membresía está cambiando, crear nueva relación y eliminar la anterior
        if (!membershipId.equals(customerMembershipRequest.getMembershipId())) {
            return changeMembership(customerId, membershipId, customerMembershipRequest);
        }

        // Valida Customer
        var customer = customerClient.findById(customerMembershipRequest.getCustomerId());
        
        // Valida Membership
        var membership = membershipClient.findMembershipById(customerMembershipRequest.getMembershipId());
        
        // Validar que el cliente y la membresía pertenezcan al mismo gym
        validateSameGym(customer.getGymId(), membership.getGymId());
        
        // Validar que el gym existe
        var gym = gymClient.findById(customer.getGymId());
        if (gym == null) {
            throw new RuntimeException("Gym no encontrado con ID: " + customer.getGymId());
        }

        CustomerMembershipMapper.copyToEntity(customerMembershipRequest, existingCustomerMembership);

        CustomerMembership updatedCustomerMembership = customerMembershipRepository.save(existingCustomerMembership);
        return CustomerMembershipMapper.toResponse(updatedCustomerMembership);
    }

    // @Override
    // public void delete(Integer idCustomer, Integer idMembership) {
    //     CustomerMembershipPk id = new CustomerMembershipPk(idCustomer, idMembership);
    //     customerMembershipRepository.deleteById(id);
    // }

    @Override
    public List<CustomerMembershipResponse> findByCustomerId(Long customerId) {
        List<CustomerMembership> customersMemberships = customerMembershipRepository.findByCustomerId(customerId);
        return customersMemberships.stream().map(CustomerMembershipMapper::toResponse).toList();
    }

    @Override
    public List<CustomerMembershipResponse> findByMembershipId(Long membershipId) {
        List<CustomerMembership> customersMemberships = customerMembershipRepository.findByMembershipId(membershipId);
        return customersMemberships.stream().map(CustomerMembershipMapper::toResponse).toList();
    }

    public List<CustomerMembershipResponse> findByGymId(Long gymId) {
        List<CustomerMembership> customersMemberships = customerMembershipRepository.findByGymId(gymId);
        return customersMemberships.stream().map(CustomerMembershipMapper::toResponse).toList();
    }

    private void validateSameGym(Long customerGymId, Long membershipGymId) {
        if (customerGymId == null || membershipGymId == null) {
            throw new RuntimeException("Tanto el cliente como la membresía deben tener un gym asignado");
        }
        
        if (!customerGymId.equals(membershipGymId)) {
            throw new RuntimeException(
                "El cliente y la membresía no pertenecen al mismo gym. " +
                "Cliente gym ID: " + customerGymId + ", " +
                "Membresía gym ID: " + membershipGymId
            );
        }
    }

    private CustomerMembershipResponse changeMembership(Long customerId, Long oldMembershipId, CustomerMembershipRequest customerMembershipRequest) {
        // Eliminar la relación anterior
        CustomerMembershipPk oldId = new CustomerMembershipPk(customerId, oldMembershipId);
        customerMembershipRepository.deleteById(oldId);
        
        // Crear nueva relación con la nueva membresía
        return create(customerMembershipRequest); // Reutiliza el método create
    }
}