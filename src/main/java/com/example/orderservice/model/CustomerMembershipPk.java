package com.example.orderservice.model;

import java.io.Serializable;
import java.util.Objects;


public class CustomerMembershipPk implements Serializable{
    private Long customerId; 
    private Long membershipId;
    
    public CustomerMembershipPk() {}
    
    public CustomerMembershipPk(Long customerId, Long membershipId) {
        this.customerId = customerId;
        this.membershipId = membershipId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof CustomerMembershipPk))
            return false;
        CustomerMembershipPk that = (CustomerMembershipPk) o;
        return Objects.equals(customerId, that.customerId) && 
               Objects.equals(membershipId, that.membershipId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(customerId, membershipId);
    }

    // Getters y setters actualizados...
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }  
}