package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService userDetailsService;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService) {

        this.jwtUtil = jwtUtil;

        this.userDetailsService =
                userDetailsService;
    }


    // =====================================================
    // FILTER
    // =====================================================

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {


        System.out.println();
        System.out.println(
                "======================================"
        );

        System.out.println(
                "JWT FILTER"
        );

        System.out.println(
                "Request: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        System.out.println(
                "======================================"
        );


        // =================================================
        // GET AUTHORIZATION HEADER
        // =================================================

        String authorizationHeader =
                request.getHeader(
                        "Authorization"
                );


        System.out.println(
                "Authorization Header: "
                        + authorizationHeader
        );


        String token = null;

        String email = null;


        // =================================================
        // CHECK BEARER TOKEN
        // =================================================

        if (authorizationHeader != null
                &&
                authorizationHeader.startsWith(
                        "Bearer "
                )) {


            token =
                    authorizationHeader.substring(7);


            System.out.println(
                    "JWT Token received"
            );


            try {

                email =
                        jwtUtil.extractEmail(
                                token
                        );


                System.out.println(
                        "Email from JWT: "
                                + email
                );


            } catch (Exception e) {

                System.out.println(
                        "JWT ERROR: "
                                + e.getMessage()
                );
            }
        }


        // =================================================
        // AUTHENTICATE USER
        // =================================================

        if (email != null
                &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        == null) {


            try {


                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(
                                        email
                                );


                System.out.println(
                        "User: "
                                + userDetails
                                        .getUsername()
                );


                System.out.println(
                        "Authorities: "
                                + userDetails
                                        .getAuthorities()
                );


                // =========================================
                // VALIDATE TOKEN
                // =========================================

                if (jwtUtil.validateToken(
                        token
                )) {


                    UsernamePasswordAuthenticationToken authentication =

                            new UsernamePasswordAuthenticationToken(

                                    userDetails,

                                    null,

                                    userDetails
                                            .getAuthorities()
                            );


                    authentication.setDetails(

                            new WebAuthenticationDetailsSource()
                                    .buildDetails(
                                            request
                                    )
                    );


                    // =====================================
                    // SET AUTHENTICATION
                    // =====================================

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );


                    System.out.println(
                            "JWT AUTHENTICATION SUCCESS"
                    );


                    System.out.println(
                            "Authorities: "
                                    + SecurityContextHolder
                                            .getContext()
                                            .getAuthentication()
                                            .getAuthorities()
                    );


                } else {


                    System.out.println(
                            "JWT TOKEN INVALID"
                    );
                }


            } catch (Exception e) {


                System.out.println(
                        "AUTHENTICATION ERROR: "
                                + e.getMessage()
                );
            }
        }


        // =================================================
        // CONTINUE REQUEST
        // =================================================

        filterChain.doFilter(
                request,
                response
        );
    }
}