package com.pulseengine.gateway.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${pulseengine.app.jwtSecret}")
    private String jwtSecret;

    @Value("${pulseengine.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

        public String generateJwtToken(String username, String tenantId, String role) {
           return Jwts.builder().subject(username).claim("tenantId", tenantId)
                   .claim("role" , role).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()
                   + jwtExpirationMs)).signWith(getSigningKey()).compact();

        }

    // 3. VALIDATE TOKEN
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true; // Token is valid and signature matches!
        } catch (JwtException | IllegalArgumentException e) {
            // Catches ExpiredJwtException, MalformedJwtException, SignatureException, etc.
            System.err.println("Invalid JWT Token: " + e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())        // Verify signature matches our key
                .build()
                .parseSignedClaims(token)           // Parse the token claims
                .getPayload().getSubject();

    }

    }

