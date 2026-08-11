package com.pulseengine.gateway.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_preference_id", nullable = false)
    private Long userPreferenceId;

    @Column(name = "channel", nullable = false)
    private String channel; // "EMAIL", "SMS", "WHATSAPP", etc.

    @Column(name = "is_enabled")
    private Boolean isEnabled;
}