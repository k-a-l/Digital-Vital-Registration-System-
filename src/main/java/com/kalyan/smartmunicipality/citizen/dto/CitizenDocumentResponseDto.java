package com.kalyan.smartmunicipality.citizen.dto;

import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenDocumentResponseDto implements Serializable {
    private Long id;
    private String fileName;
    private DocumentType documentType;
    private Long citizenId;
    private Long verifiedBy;
    private LocalDate verifiedDate;
    private LocalDate createdAt=LocalDate.now();
    private LocalDate updatedAt;
    private String fileData;

}
