package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;

import com.example.demo.entity.User;

import com.example.demo.repository.UserRepository;

import com.example.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest) {

        // Authenticate email + password
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );


        // Find user from database
        User user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        // Generate JWT
        String token =
                jwtUtil.generateToken(
                        user.getEmail()
                );


        // Return token + actual role
        return new LoginResponse(
                token,
                user.getRole()
        );
    }


    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setPhone(request.getPhone());


        if (request.getRole() == null
                || request.getRole().trim().isEmpty()) {

            user.setRole("USER");

        } else {

            user.setRole(
                    request.getRole().trim().toUpperCase()
            );
        }


        userRepository.save(user);

        return "User registered successfully";
    }
}