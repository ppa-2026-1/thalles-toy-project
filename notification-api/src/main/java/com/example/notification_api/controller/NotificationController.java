package com.example.notification_api.controller;

import com.example.notification_api.dto.CreateNotificationDTO;
import com.example.notification_api.model.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> enviar(
            @RequestBody CreateNotificationDTO dto) {

        service.enviar(dto);

        return ResponseEntity.ok().build();
    }
}