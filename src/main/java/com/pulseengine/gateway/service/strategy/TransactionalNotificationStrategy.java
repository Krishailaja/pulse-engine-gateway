package com.pulseengine.gateway.service.strategy;

import com.pulseengine.gateway.dto.BaseNotificationRequest;
import com.pulseengine.gateway.dto.TransactionalRequest;
import com.pulseengine.gateway.enums.NotificationStatus;
import com.pulseengine.gateway.model.*;
import com.pulseengine.gateway.repository.NotificationRepository;
import com.pulseengine.gateway.repository.UserChannelRepository;
import com.pulseengine.gateway.repository.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionalNotificationStrategy implements NotificationStrategy {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserChannelRepository userChannelRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void process(BaseNotificationRequest request) {
        TransactionalRequest transactionRequest = (TransactionalRequest) request;

        // 1. Fetch user preference & check opt-out
        Optional<UserPreference> preferenceOpt = userPreferenceRepository.findByUserIdAndTenantId(
                transactionRequest.getTenantId(), transactionRequest.getUserId());

        if (preferenceOpt.isEmpty() || preferenceOpt.get().isOptedOut()) {
            return;
        }

        UserPreference userPref = preferenceOpt.get();

        // 2. Fetch user channels using simple Spring Data method
        List<UserChannel> userChannels = userChannelRepository.findByUserPreferenceId(userPref.getId());

        if (userChannels.isEmpty()) {
            return;
        }

        // 3. Build notifications
        List<Notification> notificationsToSave = new ArrayList<>();
        for (UserChannel uc : userChannels) {
            Notification notif = Notification.builder()
                    .tenantId(transactionRequest.getTenantId())
                    .userId(transactionRequest.getUserId())
                    .recipient(transactionRequest.getRecipient())
                    .messageId(transactionRequest.getMessageId())
                    .notificationType(transactionRequest.getNotificationType())
                    .channel(uc.getChannel()) // Channel stored directly as String!
                    .status(NotificationStatus.PENDING)
                    .build();

            notificationsToSave.add(notif);
        }

        // 4. Save all records in one DB hit
        notificationRepository.saveAll(notificationsToSave);
    }
}