package com.kalyan.smartmunicipality.notification.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.notification.enums.DeliveryChannel;
import com.kalyan.smartmunicipality.notification.enums.NotificationEvent;
import com.kalyan.smartmunicipality.notification.enums.NotificationStatus;
import com.kalyan.smartmunicipality.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JsonBackReference(value = "notification-data")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "citizen_id", nullable = true,referencedColumnName = "id")
    private Citizen citizen;

    @JsonBackReference(value = "certificate-notification")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "certificate_id", nullable = true,referencedColumnName = "id")
    private CertificateFile certificate;

    @Transient
    private Long certificateId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Enumerated(EnumType.STRING)
    private NotificationEvent event;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @Enumerated(EnumType.STRING)
    private DeliveryChannel channel;
    @Column(columnDefinition = "TEXT")
    private String message;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentAt;
    private Boolean readStatus = false;




}
