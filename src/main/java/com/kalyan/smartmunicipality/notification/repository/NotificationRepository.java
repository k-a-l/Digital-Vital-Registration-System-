package com.kalyan.smartmunicipality.notification.repository;

import com.kalyan.smartmunicipality.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
