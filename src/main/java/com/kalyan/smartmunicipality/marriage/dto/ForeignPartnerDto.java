package com.kalyan.smartmunicipality.marriage.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignPartnerDto {
    private String fullName;
    private String nationality;
    private String passportNumber;
    private String personCitizenshipNumber;
    private String email;
    private String contactNumber;
    private LocalDate dateOfBirth;

    private FileDto passportFile;
    private FileDto photoFile;
    private FileDto personCitizenshipFile;
}