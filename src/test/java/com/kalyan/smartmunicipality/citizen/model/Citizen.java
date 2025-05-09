package com.kalyan.smartmunicipality.citizen.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Enabled
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String fatherName;
    private String motherName;
    private String grandfatherName;
    private String grandmotherName;
    private String gender;
    private String nationality;
    private String district;
    private String Municipality;
    private int wardNo;
    private String tole;
    private boolean isVerified;
    private LocalDate verifiedDate;
    private Long verifiedBy;
    private LocalDate createdAt;
    private LocalDate updatedAt;


}
