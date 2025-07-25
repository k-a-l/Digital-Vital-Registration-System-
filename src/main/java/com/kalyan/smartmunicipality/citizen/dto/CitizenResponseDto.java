package com.kalyan.smartmunicipality.citizen.dto;

import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.enums.MarriageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenResponseDto implements Serializable {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String spouseName;
    private CitizenStatus status;

    private Long phoneNo;
    private Gender gender;
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
    private String reasonForRejection;
    private LocalDate verifiedDate;
    private Long verifiedBy;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private MarriageStatus marriageStatus;
    private String citizenshipNumber;


}
