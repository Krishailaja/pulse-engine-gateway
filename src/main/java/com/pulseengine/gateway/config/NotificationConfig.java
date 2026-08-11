package com.pulseengine.gateway.config;


import com.pulseengine.gateway.enums.NotificationType;
import com.pulseengine.gateway.service.strategy.CampaignNotificationStrategy;
import com.pulseengine.gateway.service.strategy.OtpNotificationStrategy;
import com.pulseengine.gateway.service.strategy.TransactionalNotificationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class NotificationConfig {

    @Bean
    public Map<NotificationType, Object> strategyMap
            (TransactionalNotificationStrategy transactionNotificationStrategy,
             CampaignNotificationStrategy campaignNotificationStrategy,
             OtpNotificationStrategy otpNotificationStrategy) {

        return  Map.of(NotificationType.TRANSACTION , transactionNotificationStrategy,
                NotificationType.CAMPAIGN , campaignNotificationStrategy,
                NotificationType.OTP , otpNotificationStrategy);
    }
}
