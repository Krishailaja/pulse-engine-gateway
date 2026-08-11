package com.pulseengine.gateway.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwtToken = parseToken(request);

            if(jwtToken!= null && jwtUtils.validateJwtToken(jwtToken)) {
                String username = jwtUtils.getUsernameFromJwtToken(jwtToken);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // STEP 5: Create Spring Security's internal auth object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // STEP 6: Mark request as AUTHENTICATED inside Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        // STEP 7: Let the request pass to the next filter/controller
        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        // 1. Check if header is NOT null and NOT empty, AND starts with "Bearer "
        if (org.springframework.util.StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // 2. Strip "Bearer " (7 characters) and return only the raw JWT string
            return headerAuth.substring(7);
        }

        return null;
    }

    }




