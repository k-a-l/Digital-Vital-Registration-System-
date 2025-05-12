package com.kalyan.smartmunicipality.certificate.model;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
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
public class BirthCertificateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ChildName;
    private String gender;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "citizen_id", referencedColumnName = "id",nullable = false)
    private Citizen citizen;

    private Long requestedBy; //Citizen Id
    private LocalDate requestedAt;

    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "birthCertificateRequest")
    private CertificateFile certificateFile;




}
