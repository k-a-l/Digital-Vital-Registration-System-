package com.kalyan.smartmunicipality.marriage.dto;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenDto {
    private Long id;
    private String fullName;
    private String gender;
    private String citizenshipNumber;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
}
