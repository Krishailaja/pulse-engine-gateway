package com.pulseengine.gateway.dto;

import com.pulseengine.gateway.enums.NotificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionalRequest extends BaseNotificationRequest {

    private String userId;
    private String recipient;
    private String messageId;   // Actual body text or code
    private NotificationType notificationType;

}