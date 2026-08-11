package com.pulseengine.gateway.dto;

import com.pulseengine.gateway.enums.NotificationType;
import lombok.Data;
import java.util.Map;

@Data
public abstract class BaseNotificationRequest {

    private String tenantId;
    private Map<String, String> metadata;
    private NotificationType notificationType;
}