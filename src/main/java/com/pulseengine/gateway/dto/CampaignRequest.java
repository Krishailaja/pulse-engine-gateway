package com.pulseengine.gateway.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CampaignRequest extends BaseNotificationRequest {

    private String campaignId;
    private String userId; // Target user ID
    private String recipient; // Target email/phone
    private String messageId; // Template ID for campaign content
    private List<String> targetChannels; // Optional: restrict campaign to specific channels (e.g. ["EMAIL", "PUSH"])
}