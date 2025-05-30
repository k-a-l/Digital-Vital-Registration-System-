package com.kalyan.smartmunicipality.citizen.model;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "citizens")
public class Citizen implements Serializable {
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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    private String tole;
    private CitizenStatus status;
    private String reasonForRejection;
    private LocalDate verifiedDate;
    private Long verifiedBy;
    @CreationTimestamp
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "citizen",cascade = CascadeType.ALL)
    private List<CitizenDocument> documents;

    @OneToMany(mappedBy = "citizen",cascade = CascadeType.ALL)
    private List<BirthCertificateRequest> birthCertificateRequests;

    @OneToMany(mappedBy = "citizenId",cascade = CascadeType.ALL)
    private List<CertificateFile> certificateFiles;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "citizen")
    private User user;


}
