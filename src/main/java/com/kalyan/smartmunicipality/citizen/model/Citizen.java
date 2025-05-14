package com.kalyan.smartmunicipality.citizen.model;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "citizens")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String spouseName;
    private LocalDate dateOfBirth;
    @Column(unique = true, nullable = false)
    private Long phoneNo;
    private String fatherName;
    private String motherName;
    private String grandfatherName;
    private String grandmotherName;
    private String gender;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    private String tole;
    private boolean isVerified;
    private LocalDate verifiedDate;
    private Long verifiedBy;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "citizenId",cascade = CascadeType.ALL)
    private List<CitizenDocument> documents;

    @OneToMany(mappedBy = "citizen",cascade = CascadeType.ALL)
    private List<BirthCertificateRequest> birthCertificateRequests;

    @OneToMany(mappedBy = "citizenId",cascade = CascadeType.ALL)
    private List<CertificateFile> certificateFiles;


}
