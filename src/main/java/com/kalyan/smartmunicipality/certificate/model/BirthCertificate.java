package com.kalyan.smartmunicipality.certificate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BirthCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ChildName;
    private String gender;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "citizen_id", referencedColumnName = "id",nullable = false)

    private Citizen citizenId;

    private Long requestedBy; //Citizen Id
    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    @Lob

    @Column(name = "certificate_file", nullable = true)
    private byte[] certificateFile;
    @Column(name = "file_name", nullable = true)

    private String fileName;
    private LocalDate verifiedAt;
    private Long verifiedBy;



}
