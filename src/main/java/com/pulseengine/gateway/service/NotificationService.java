package com.pulseengine.gateway.service;


import com.pulseengine.gateway.dto.BaseNotificationRequest;
import com.pulseengine.gateway.enums.NotificationType;
import com.pulseengine.gateway.service.strategy.NotificationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationService {
    private final Map<NotificationType, NotificationStrategy> strategyMap;

    public void processRequest(BaseNotificationRequest baseNotificationRequest) {
        NotificationStrategy strategy = strategyMap.get(baseNotificationRequest.getNotificationType());

        if(strategy == null) {
            throw new NullPointerException("Strategy is not defined in the strategyMap");
        }

        strategy.process(baseNotificationRequest);
    }

}
