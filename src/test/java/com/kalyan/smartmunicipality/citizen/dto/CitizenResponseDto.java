package com.kalyan.smartmunicipality.citizen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenResponseDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    private String tole;
    private LocalDate dateOfBirth;
    private String fatherName;
    private String motherName;
    private String grandfatherName;
    private String grandmotherName;
    private boolean isVerified;
    private LocalDate verifiedDate;
    private Long verifiedBy;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
