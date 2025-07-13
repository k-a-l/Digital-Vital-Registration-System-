package com.kalyan.smartmunicipality.citizen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.enums.MarriageStatus;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import com.kalyan.smartmunicipality.notification.model.Notification;
import com.kalyan.smartmunicipality.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@ToString(onlyExplicitlyIncluded = true)

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
    private MarriageStatus marriageStatus;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    private String tole;
    private CitizenStatus status;
    private String reasonForRejection;
    private LocalDate verifiedDate;
    @Column(unique = true)
    private String citizenshipNumber;
    private Long verifiedBy;
    @CreationTimestamp
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @JsonManagedReference(value = "citizen-document")
    @OneToMany(mappedBy = "citizen",cascade = CascadeType.ALL)
    private List<CitizenDocument> documents;

    @JsonManagedReference(value = "certificate-file-citizen")
    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL)
    private List<CertificateFile> certificateFiles;

    @JsonManagedReference(value = "birth-certificate-request")
    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL)
    private List<BirthCertificateRequest> birthCertificateRequests;


    // Add these fields
    @Column(name = "user_id_value")
    private Long userId;

    @Column(name = "user_email_value")
    private String userEmail;

    // Keep the existing relationship
    @JsonManagedReference("user-data")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonBackReference("notification-data")
    @OneToMany(mappedBy = "citizen",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications =new ArrayList<>();

    @JsonManagedReference(value = "death-certificate-request")
    @OneToMany(mappedBy = "requestedBy")
    private List<DeathCertificateRequest> deathCertificateRequests;

    @JsonManagedReference(value = "marriage-request-husband")
    @OneToMany(mappedBy = "husband", cascade = CascadeType.ALL)
    private List<MarriageCertificateRequest> marriageRequestsAsHusband;

    @JsonManagedReference(value = "marriage-request-wife")
    @OneToMany(mappedBy = "wife", cascade = CascadeType.ALL)
    private List<MarriageCertificateRequest> marriageRequestsAsWife;

    @JsonManagedReference(value = "marriage-request")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestedBy")
    private List<MarriageCertificateRequest> requestedBy;




}
