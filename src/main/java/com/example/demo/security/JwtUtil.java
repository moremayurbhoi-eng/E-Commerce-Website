package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {


    private static final String SECRET =
            "MySecretKeyForEcommerceApplication2026SecureKey1234567890";


    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes(
                            StandardCharsets.UTF_8
                    )
            );


    // =====================================================
    // GENERATE TOKEN
    // =====================================================

    public String generateToken(
            String email) {


        Date now =
                new Date();


        Date expiry =
                new Date(
                        now.getTime()
                                + 60 * 60 * 1000
                );


        return Jwts.builder()

                .subject(email)

                .issuedAt(now)

                .expiration(expiry)

                .signWith(secretKey)

                .compact();
    }


    // =====================================================
    // EXTRACT EMAIL
    // =====================================================

    public String extractEmail(
            String token) {


        Claims claims =
                Jwts.parser()

                        .verifyWith(
                                secretKey
                        )

                        .build()

                        .parseSignedClaims(
                                token
                        )

                        .getPayload();


        return claims.getSubject();
    }


    // =====================================================
    // VALIDATE TOKEN
    // =====================================================

    public boolean validateToken(
            String token) {


        try {


            Jwts.parser()

                    .verifyWith(
                            secretKey
                    )

                    .build()

                    .parseSignedClaims(
                            token
                    );


            return true;


        } catch (Exception e) {


            System.out.println(
                    "JWT Validation Error: "
                            + e.getMessage()
            );


            return false;
        }
    }
}