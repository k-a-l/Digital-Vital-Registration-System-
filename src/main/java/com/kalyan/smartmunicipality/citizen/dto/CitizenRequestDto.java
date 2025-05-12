package com.kalyan.smartmunicipality.citizen.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenRequestDto {
    @NotNull(message = "Cannot be Null")
    private String firstName;
    @NotNull(message = "Cannot be Null")
    private String lastName;
    @NotNull(message = "Cannot be Null")
    private String gender;
    @NotNull(message = "Cannot be Null")
    private String nationality;
    @NotNull(message = "Cannot be Null")
    private String district;
    @NotNull(message = "Cannot be Null")
    private String municipality;
    @NotNull(message = "Cannot be Null")
    private int wardNo;
    @NotNull(message = "Cannot be Null")
    private String tole;
    private String fatherName;
    private String motherName;
    private String spouseName;

    private Long phoneNo;
    private boolean isVerified=false;
    private String grandfatherName;
    private String grandmotherName;
    @NotNull(message = "Cannot be Null")
    private LocalDate dateOfBirth;
    private String middleName;
    private LocalDate verifiedDate=LocalDate.now();
    private LocalDate createdAt=LocalDate.now();
    private LocalDate updatedAt=LocalDate.now();
    private Long verifiedBy;


}
