package com.pulseengine.gateway.controller;

import com.pulseengine.gateway.dto.LoginRequest;
import com.pulseengine.gateway.security.jwt.JwtUtils;
import com.pulseengine.gateway.security.services.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 2. Set Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Extract UserDetailsImpl from Principal
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // 4. Extract role (getting the first authority string)
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");

            // 5. Generate token using your custom method parameters
            String jwt = jwtUtils.generateJwtToken(
                    userDetails.getUsername(),
                    userDetails.getTenantId(), // Assumes getTenantId() exists on UserDetailsImpl
                    role
            );

            // 6. Return response
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "type", "Bearer"
            ));
        } catch (AuthenticationException e) {
            // If credentials are wrong, log it so you can see it in terminal!
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}