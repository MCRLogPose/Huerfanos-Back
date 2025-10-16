// auth/dto/RegisterRequest.java
package com.bakerysystem.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
