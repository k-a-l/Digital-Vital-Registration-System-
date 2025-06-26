package com.kalyan.smartmunicipality.notification.service;

import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import com.kalyan.smartmunicipality.kafka.producer.KafkaNotificationProducer;
import com.kalyan.smartmunicipality.notification.enums.NotificationStatus;
import com.kalyan.smartmunicipality.notification.model.Notification;
import com.kalyan.smartmunicipality.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@ToString(onlyExplicitlyIncluded = true)

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CitizenRepository citizenRepository;
    private final CertificateFileRepository certificateFileRepository;
    private final KafkaNotificationProducer kafkaNotificationProducer;

    public void sendAndDispatch(Notification notification) {
        log.info("Preparing to send notification: {}", notification);

        Long citizenId = notification.getCitizen() != null ? notification.getCitizen().getId() : null;
        if (citizenId == null) {
            throw new RuntimeException("Citizen is missing in the notification.");
        }

        // ✅ Fetch fully managed Citizen entity
        var citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found with id: " + citizenId));

        Notification.NotificationBuilder builder = Notification.builder()
                .citizen(citizen) // ✅ use managed entity
                .event(notification.getEvent())
                .channel(notification.getChannel())
                .message(notification.getMessage())
                .email(notification.getEmail())
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .type(notification.getType())
                .readStatus(Boolean.FALSE)
                .certificateId(notification.getCertificateId())
                .sentAt(LocalDateTime.now());

        if (notification.getCertificate() != null && notification.getCertificate().getId() != null) {
            certificateFileRepository.findById(notification.getCertificate().getId())
                    .ifPresent(builder::certificate);

        }
        Notification savedNotification = notificationRepository.save(builder.build());

        kafkaNotificationProducer.sendNotification(savedNotification);
        log.info("Notification sent to Kafka and saved to DB: {}", savedNotification);
    }



}



