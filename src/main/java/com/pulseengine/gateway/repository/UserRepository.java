package com.pulseengine.gateway.repository;

import com.pulseengine.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 1. Fetch user by username (Includes their email, password, tenantId, and role automatically!)
    Optional<User> findByUsername(String username);

    // 2. Check if a username already exists during registration
    Boolean existsByUsername(String username);
}
