package com.kalyan.smartmunicipality.marriage.dto;

import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MarriageCertificateResponseDto implements Serializable {
    private Long id;
    private String partnerName;
    private String partnerEmail;
    private String marriagePlace;
    private LocalDate marriageDate;
    private CertificateStatus marriageStatus;
    private String requestedBy;
    private String scheduledTime;
}
