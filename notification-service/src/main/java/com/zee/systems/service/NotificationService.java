package com.zee.systems.service;

import com.zee.systems.entity.Notification;
import com.zee.systems.datatype.NotificationChannel;
import com.zee.systems.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification sendNotification(NotificationChannel channel,
                                         String recipient,
                                         String message) {

        Notification notification = Notification.builder()
                .channel(channel)
                .recipient(recipient)
                .message(message)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }
}