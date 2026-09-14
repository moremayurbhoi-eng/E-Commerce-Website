package com.example.demo.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    // =====================================================
    // DEPENDENCIES
    // =====================================================

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // =====================================================
    // SECURITY FILTER CHAIN
    // =====================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // =================================================
            // CORS
            // =================================================

            .cors(cors ->
                cors.configurationSource(
                    corsConfigurationSource()
                )
            )

            // =================================================
            // CSRF
            // =================================================

            .csrf(csrf -> csrf.disable())

            // =================================================
            // SESSION
            // =================================================

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // =================================================
            // AUTHORIZATION
            // =================================================

            .authorizeHttpRequests(auth -> auth

                // =================================================
                // OPTIONS
                // =================================================

                .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
                )
                .permitAll()

                // =================================================
                // LOGIN + REGISTER
                // =================================================

                .requestMatchers(
                    "/auth/**"
                )
                .permitAll()

                // =================================================
                // PRODUCT IMAGES
                // =================================================

                .requestMatchers(
                    "/uploads/**"
                )
                .permitAll()

                // =================================================
                // VIEW PRODUCTS
                // =================================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/products",
                    "/products/**"
                )
                .permitAll()

                // =================================================
                // ADD PRODUCT - ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.POST,
                    "/products/**"
                )
                .hasRole("ADMIN")

                // =================================================
                // UPDATE PRODUCT - ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.PUT,
                    "/products/**"
                )
                .hasRole("ADMIN")

                // =================================================
                // DELETE PRODUCT - ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/products/**"
                )
                .hasRole("ADMIN")

                // =================================================
                // CATEGORIES - ADMIN
                // =================================================

                .requestMatchers(
                    "/categories/**"
                )
                .hasRole("ADMIN")

                // =================================================
                // USERS - ADMIN
                // =================================================

                .requestMatchers(
                    "/users/**"
                )
                .hasRole("ADMIN")

                // =================================================
                // CART
                // =================================================

                .requestMatchers(
                    "/cart/**"
                )
                .hasAnyRole(
                    "USER",
                    "ADMIN"
                )

                // =================================================
                // ORDERS
                // =================================================

                .requestMatchers(
                    "/orders/**"
                )
                .hasAnyRole(
                    "USER",
                    "ADMIN"
                )

                // =================================================
                // EVERYTHING ELSE
                // =================================================

                .anyRequest()
                .authenticated()
            )

            // =================================================
            // AUTHENTICATION PROVIDER
            // =================================================

            .authenticationProvider(
                authenticationProvider()
            )

            // =================================================
            // JWT FILTER
            // =================================================

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // =====================================================
    // CORS CONFIGURATION
    // =====================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
            Arrays.asList(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
            )
        );

        configuration.setAllowedMethods(
            Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
            )
        );

        configuration.setAllowedHeaders(
            Arrays.asList("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
            "/**",
            configuration
        );

        return source;
    }

    // =====================================================
    // AUTHENTICATION PROVIDER
    // =====================================================

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                    userDetailsService
                );

        provider.setPasswordEncoder(
            passwordEncoder
        );

        return provider;
    }

    // =====================================================
    // AUTHENTICATION MANAGER
    // =====================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}