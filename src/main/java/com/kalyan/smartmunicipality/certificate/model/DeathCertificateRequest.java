package com.kalyan.smartmunicipality.certificate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.Relation;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class DeathCertificateRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deceasedName;
    private String fatherName;
    private String motherName;
    @Past(message = "Should be in the Past")
    private LocalDate deceasedDate;
    @Past(message = "Should be in the Past")
    private LocalDate dateOfBirth;
    private String causeOfDeath;
    private String placeOfDeath;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    @Column(unique = true, nullable = true)
    private String citizenshipNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Relation relation;
    @Enumerated(EnumType.STRING)
    private CertificateStatus certificateStatus;


    @JsonBackReference(value = "death-certificate-request")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_id")
    private Citizen requestedBy;

    private LocalDateTime requestedAt;

    @JsonBackReference(value = "death-request")
    @OneToOne
    @JoinColumn(name= "hospital_doc_id")
    private CitizenDocument citizenDocument;

    @JsonManagedReference(value = "death-certificate")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "deathCertificateRequest")
    private CertificateFile certificateFile;

    @ManyToOne(fetch = FetchType.LAZY)
    private StaffUser staffUser;











}
