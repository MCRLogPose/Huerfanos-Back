package com.bakerysystem.auth.mapper;

import com.bakerysystem.auth.dto.RoleRequest;
import com.bakerysystem.auth.dto.RoleResponse;
import com.bakerysystem.auth.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toEntity(RoleRequest dto) {
        return Role.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public RoleResponse toDTO(Role entity) {
        RoleResponse dto = new RoleResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
