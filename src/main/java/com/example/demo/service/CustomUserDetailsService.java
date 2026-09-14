package com.example.demo.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {


    private final UserRepository userRepository;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    // =====================================================
    // LOAD USER
    // =====================================================

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found: "
                                                + email
                                )
                        );


        // =================================================
        // GET ROLE FROM DATABASE
        // =================================================

        String role = user.getRole();


        System.out.println(
                "================================="
        );

        System.out.println(
                "LOGIN USER"
        );

        System.out.println(
                "Email      : "
                        + user.getEmail()
        );

        System.out.println(
                "DB Role    : ["
                        + role
                        + "]"
        );


        // =================================================
        // CHECK ROLE
        // =================================================

        if (role == null ||
                role.trim().isEmpty()) {

            throw new UsernameNotFoundException(
                    "User has no role: "
                            + email
            );
        }


        // Remove spaces

        role = role.trim().toUpperCase();


        // =================================================
        // REMOVE ROLE_ IF ALREADY PRESENT
        // =================================================

        if (role.startsWith("ROLE_")) {

            role = role.substring(5);
        }


        // =================================================
        // CREATE SPRING AUTHORITY
        // =================================================

        String authority =
                "ROLE_" + role;


        System.out.println(
                "Authority  : ["
                        + authority
                        + "]"
        );

        System.out.println(
                "================================="
        );


        // =================================================
        // RETURN USER DETAILS
        // =================================================

        return new org.springframework.security.core.userdetails.User(

                user.getEmail(),

                user.getPassword(),

                Collections.singletonList(
                        new SimpleGrantedAuthority(
                                authority
                        )
                )
        );
    }
}