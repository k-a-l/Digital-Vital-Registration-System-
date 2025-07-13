package com.kalyan.smartmunicipality.certificate.certificateFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.CertificateType;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import com.kalyan.smartmunicipality.notification.model.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CertificateFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;


    @ToString.Exclude
    @JsonBackReference(value = "certificate-file-birth-request")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_certificate_request_id", referencedColumnName = "id", nullable = true)
    private BirthCertificateRequest birthCertificateRequest;


    @JsonBackReference(value = "certificate-file-marriage-request")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marriage_certificate_request_id")
    private MarriageCertificateRequest marriageCertificateRequest;


    @JsonBackReference(value = "certificate-file-citizen")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id", nullable = false)
    private Citizen citizen;


    private Long verifiedBy;
    private LocalDate verifiedAt;

    private CertificateStatus status;
    private CertificateType certificateType;

    @Lob
    @JsonIgnore
    private byte[] fileData;

    @Lob
    @JsonIgnore
    @Column(length = 10000)
    private String digitalSignature;

    @Lob
    @JsonIgnore
    @Column(length = 10000)
    private String modulus;


    @Column(length = 10000)
    private String publicKey;

    @Column(unique = true, nullable = false, updatable = false)
    private String referenceNumber;

    @JsonManagedReference(value = "certificate-notification")
    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();


    @ToString.Exclude
    @JsonBackReference(value = "death-certificate")
    @OneToOne
    @JoinColumn(name = "dathCertficateRequest_id",nullable = true)
    private DeathCertificateRequest deathCertificateRequest;

}
