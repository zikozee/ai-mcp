package com.zee.systems.repository;

import com.zee.systems.entity.Notification;

import org.springframework.data.repository.ListCrudRepository;

public interface NotificationRepository extends ListCrudRepository<Notification, Long> {
}