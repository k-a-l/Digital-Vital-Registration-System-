package com.kalyan.smartmunicipality.certificate.certificateFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.CertificateType;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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

    @JsonBackReference(value = "certificate-file-birth-request")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_certificate_request_id", referencedColumnName = "id", nullable = false)
    private BirthCertificateRequest birthCertificateRequest;

    @JsonBackReference(value = "certificate-file-citizen")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id", nullable = false)
    private Citizen citizenId;


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




}
