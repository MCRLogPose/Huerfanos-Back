package com.huerfanosSRL.Huerfanosback.auth.controller;

import com.huerfanosSRL.Huerfanosback.auth.dto.LoginRequest;
import com.huerfanosSRL.Huerfanosback.auth.dto.LoginResponse;
import com.huerfanosSRL.Huerfanosback.auth.dto.RegisterRequest;
import com.huerfanosSRL.Huerfanosback.auth.dto.RegisterResponse;
import com.huerfanosSRL.Huerfanosback.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;



}
