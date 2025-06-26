package com.kalyan.smartmunicipality.kafka.producer;

import com.kalyan.smartmunicipality.notification.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationProducer {
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        kafkaTemplate.send("notification-topic", notification);
        log.info("Notification sent to topic {}", notification.getId());
    }
}
