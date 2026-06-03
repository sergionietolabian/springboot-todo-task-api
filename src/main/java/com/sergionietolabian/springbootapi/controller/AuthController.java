package com.sergionietolabian.springbootapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergionietolabian.springbootapi.dto.AuthRequest;
import com.sergionietolabian.springbootapi.dto.AuthResponse;
import com.sergionietolabian.springbootapi.dto.RegisterRequestDTO;
import com.sergionietolabian.springbootapi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request) {

        return authService.login(request);
    }
}