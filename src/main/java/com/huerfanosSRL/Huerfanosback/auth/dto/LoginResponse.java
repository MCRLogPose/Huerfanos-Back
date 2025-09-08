package com.huerfanosSRL.Huerfanosback.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String roleName;
    private List<String> modules;
}
