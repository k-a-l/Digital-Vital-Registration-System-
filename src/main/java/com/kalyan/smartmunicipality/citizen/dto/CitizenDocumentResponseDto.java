package com.kalyan.smartmunicipality.citizen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenDocumentResponseDto {
    private Long id;
    private String fileName;
    private String documentType;
    private String verifiedBy;
    private String verifiedDate;
    private String createdAt;
    private String updatedAt;
}
