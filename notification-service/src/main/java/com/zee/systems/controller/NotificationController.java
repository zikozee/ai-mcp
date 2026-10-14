package com.zee.systems.controller;

import com.zee.systems.entity.Notification;
import com.zee.systems.datatype.NotificationChannel;
import com.zee.systems.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public Notification sendNotification(@RequestParam NotificationChannel channel,
                                         @RequestParam String recipient,
                                         @RequestParam String message) {

        return notificationService.sendNotification(channel, recipient, message);
    }

    @GetMapping
    public List<Notification> getNotifications() {
        return notificationService.getNotifications();
    }
}