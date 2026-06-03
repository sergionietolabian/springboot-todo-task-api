package com.sergionietolabian.springbootapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sergionietolabian.springbootapi.dto.AuthRequest;
import com.sergionietolabian.springbootapi.dto.AuthResponse;
import com.sergionietolabian.springbootapi.dto.RegisterRequestDTO;
import com.sergionietolabian.springbootapi.entity.User;
import com.sergionietolabian.springbootapi.enums.Role;
import com.sergionietolabian.springbootapi.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}