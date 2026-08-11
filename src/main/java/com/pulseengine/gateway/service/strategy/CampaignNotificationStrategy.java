package com.pulseengine.gateway.service.strategy;

import com.pulseengine.gateway.dto.BaseNotificationRequest;
import com.pulseengine.gateway.dto.CampaignRequest;
import com.pulseengine.gateway.enums.NotificationStatus;
import com.pulseengine.gateway.model.Notification;
import com.pulseengine.gateway.model.UserChannel;
import com.pulseengine.gateway.model.UserPreference;
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
public class CampaignNotificationStrategy implements NotificationStrategy {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserChannelRepository userChannelRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void process(BaseNotificationRequest request) {
        CampaignRequest campaignRequest = (CampaignRequest) request;

        // 1. Fetch preference for the targeted user
        Optional<UserPreference> preferenceOpt = userPreferenceRepository.findByUserIdAndTenantId(
                campaignRequest.getTenantId(), campaignRequest.getUserId());

        // 2. Stop processing if user record doesn't exist or globally opted out
        if (preferenceOpt.isEmpty() || preferenceOpt.get().isOptedOut()) {
            return;
        }

        UserPreference userPref = preferenceOpt.get();

        // 3. Retrieve user's subscribed active channels
        List<UserChannel> userChannels = userChannelRepository.findByUserPreferenceId(userPref.getId());

        if (userChannels.isEmpty()) {
            return;
        }

        // 4. Build notifications for each eligible channel
        List<Notification> notificationsToSave = new ArrayList<>();

        for (UserChannel uc : userChannels) {
            // Optional filter: If the campaign restricts to certain channels (e.g., EMAIL only)
            if (campaignRequest.getTargetChannels() != null &&
                    !campaignRequest.getTargetChannels().contains(uc.getChannel())) {
                continue; // Skip channel if not targeted by campaign
            }

            Notification notif = Notification.builder()
                    .tenantId(campaignRequest.getTenantId())
                    .userId(campaignRequest.getUserId())
                    .recipient(campaignRequest.getRecipient())
                    .messageId(campaignRequest.getMessageId())
                    .notificationType(campaignRequest.getNotificationType())
                    .channel(uc.getChannel()) // Pure String (EMAIL, SMS, PUSH, etc.)
                    .status(NotificationStatus.PENDING)
                    .build();

            notificationsToSave.add(notif);
        }
        if (!notificationsToSave.isEmpty()) {
            notificationRepository.saveAll(notificationsToSave);
        }
    }
}