package com.example.orderservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("User identifier")
    private Long id;

    private String userName;
    private String mail;
    private String phone;
    private String name;
    private Integer status;
    private Long roleId;
    private String authority;
    private Long gymId;
}