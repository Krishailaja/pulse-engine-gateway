package com.pulseengine.gateway.controller;


import com.pulseengine.gateway.dto.BaseNotificationRequest;
import com.pulseengine.gateway.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public RequestBody sendNotification(@RequestBody BaseNotificationRequest notificationRequest) {
        notificationService.processRequest(notificationRequest);
        return (RequestBody) ResponseEntity.ok("Notification successfully sent to be processed");

    }
}
