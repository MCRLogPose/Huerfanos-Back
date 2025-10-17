package com.bakerysystem.auth.service;

import com.bakerysystem.auth.dto.RoleRequest;
import com.bakerysystem.auth.dto.RoleResponse;
import com.bakerysystem.auth.mapper.RoleMapper;
import com.bakerysystem.auth.model.Role;
import com.bakerysystem.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse create(RoleRequest dto) {
        if (roleRepository.existsByName(dto.getName())) {
            throw new RuntimeException("El rol ya existe");
        }
        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleResponse update(Long id, RoleRequest dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return roleMapper.toDTO(roleRepository.save(role));
    }

    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        roleRepository.deleteById(id);
    }
}
