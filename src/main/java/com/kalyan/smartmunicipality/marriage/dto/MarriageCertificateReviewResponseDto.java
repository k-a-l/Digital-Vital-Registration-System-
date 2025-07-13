package com.kalyan.smartmunicipality.marriage.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarriageCertificateReviewResponseDto {
    private Long id;

    private String requestedByFullName;
    private String status;
    private String videoVerificationLink;
    private String placeOfMarriage;
    private LocalDate marriageDate;
    private LocalDate requestedAt;
    private LocalDate verifiedAt;

    private CitizenDto partner;
    private ForeignPartnerDto foreignPartner;

    private FileDto wardOfficeFile;
    private FileDto marriagePhoto;
}
