// auth/dto/AuthRequest.java
package com.bakerysystem.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
