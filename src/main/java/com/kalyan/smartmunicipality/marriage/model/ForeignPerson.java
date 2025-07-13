package com.kalyan.smartmunicipality.marriage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignPerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String nationality;
    private String passportNumber;
    private String personCitizenshipNumber; // Optional: other ID type
    private String email;
    private String contactNumber;

    private LocalDate dateOfBirth;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String passportFileData;
    private String passportFileName;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String photoFileData;
    private String photoFileName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String personCitizenshipFileData;
    private String personCitizenshipFileName;

    @JsonManagedReference(value = "foreign-marriage")
    @OneToOne(mappedBy = "foreignPartner")
    private MarriageCertificateRequest marriageCertificateRequest;
}
