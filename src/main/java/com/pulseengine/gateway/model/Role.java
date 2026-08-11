package com.pulseengine.gateway.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Role names in Spring Security usually start with "ROLE_" (e.g., "ROLE_TENANT", "ROLE_ADMIN")
    @Column(nullable = false, unique = true)
    private String name;
}
