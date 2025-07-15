package com.kalyan.smartmunicipality.marriage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarriageCertificateRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference(value = "marriage-request")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by")
    private Citizen requestedBy;


    // Citizen husband or wife (if not foreign)
    @JsonBackReference(value = "marriage-request-husband")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "husband_id", referencedColumnName = "id")
    private Citizen husband;

    @JsonBackReference(value = "marriage-request-wife")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wife_id", referencedColumnName = "id")
    private Citizen wife;

    // Foreign husband or wife
    @JsonBackReference(value = "foreign-marriage")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "foreign_partner_id")
    private ForeignPerson foreignPartner;


    private Long partnerId;

    private String scheduledTime;




    private LocalDate marriageDate;
    private String municipality;

    // Ward office recommendation file
    @Lob
    @Column(columnDefinition = "TEXT")
    private String wardOfficeFileData;
    private String wardOfficeFileName;

    // Verification picture of both
    @Lob
    @Column(columnDefinition = "TEXT")
    private String marriagePhotoData;
    private String marriagePhotoFileName;

    // Status: PENDING, VERIFIED, REJECTED, APPROVED
    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    private String videoVerificationLink;
    private LocalDate requestedAt;
    private LocalDate verifiedAt;
    private Long verifiedBy;

    @JsonManagedReference(value = "certificate-file-marriage-request")
    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "marriageCertificateRequest")
    private CertificateFile certificateFile;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private StaffUser staffUser;



    @PrePersist
    private void prePersist() {
        if (requestedAt == null) {
            requestedAt = LocalDate.now();
        }
        if(status == null) {
            setStatus(CertificateStatus.PENDING);
        }
    }
}
