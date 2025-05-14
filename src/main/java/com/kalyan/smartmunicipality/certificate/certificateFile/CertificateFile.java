package com.kalyan.smartmunicipality.certificate.certificateFile;

import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.CertificateType;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CertificateFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_certificate_request_id", referencedColumnName = "id",nullable = false)
    private BirthCertificateRequest birthCertificateRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id",nullable = false)
    private Citizen citizenId;

    private Long verifiedBy;
    private LocalDate verifiedAt;

    private CertificateStatus status;
    private CertificateType certificateType;

    @Lob
    private byte[] fileData;


}
