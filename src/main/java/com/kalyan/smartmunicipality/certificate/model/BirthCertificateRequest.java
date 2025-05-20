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

@Entity
@Table(
        name = "birth_certificate_request",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_child_date_citizen",
                columnNames = {"child_name", "date_of_birth", "citizen_id"}
        )
)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthCertificateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_name", nullable = false)
    private String childName;

    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "citizen_id", referencedColumnName = "id", nullable = false)
    private Citizen citizen;

    private Long requestedBy;

    private LocalDate requestedAt;

    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "birthCertificateRequest")
    private CertificateFile certificateFile;
}
