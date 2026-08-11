package com.pulseengine.gateway.model;

import com.pulseengine.gateway.enums.NotificationStatus;
import com.pulseengine.gateway.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String messageId; // FK pointing to Templates / Messages table

    @Column(nullable = false)
    private String recipient; // e.g., user@email.com or +1234567890

    // Fixed: Removed @Enumerated since channel is a String
    @Column(nullable = false)
    private String channel; // "EMAIL", "SMS", "PUSH", "WHATSAPP"

    // Fixed: Kept notificationType and added @Enumerated
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType; // TRANSACTIONAL, MARKETING, OTP

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status; // PENDING, SENT, FAILED

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = NotificationStatus.PENDING; // Default status on insert
        }
    }
}