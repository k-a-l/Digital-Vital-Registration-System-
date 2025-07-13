package com.kalyan.smartmunicipality.citizen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@ToString(onlyExplicitlyIncluded = true)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CitizenDocument implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Many document belong to one citizen
    @JsonBackReference(value = "citizen-document")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id",nullable = false)
    private Citizen citizen;

    @JsonManagedReference(value = "death-request")
    @ManyToOne(fetch = FetchType.LAZY)
    private DeathCertificateRequest deathCertificateRequest;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String fileData;
    private String fileName;
    private LocalDate uploadDate;
    private Long verifiedBy;
    private LocalDate verifiedDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
