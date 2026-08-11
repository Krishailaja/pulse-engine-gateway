package com.pulseengine.gateway.service.strategy;

import com.pulseengine.gateway.dto.BaseNotificationRequest;

public interface NotificationStrategy {

    void process(BaseNotificationRequest notificationRequest);
}
