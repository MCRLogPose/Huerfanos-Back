package com.bakerysystem.auth.dto;

import lombok.Data;

@Data
public class RoleRequest {
    private String name;
    private String description;
}