package com.huerfanosSRL.Huerfanosback.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String email;
    private String roleName;
    private String address;
    private List<String> modules;
}
