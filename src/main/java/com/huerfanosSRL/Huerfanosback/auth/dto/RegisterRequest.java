package com.huerfanosSRL.Huerfanosback.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    private String email;
    private String address;
    private String roleName;
}
