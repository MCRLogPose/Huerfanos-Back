package com.bakerysystem.users.mapper;

import com.bakerysystem.auth.model.User;
import com.bakerysystem.users.dto.UserResponseDTO;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .registered(user.getRegistered())
                .isActive(user.getIsActive())
                .roles(
                        user.getRoles().stream()
                                .map(role -> role.getName())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
