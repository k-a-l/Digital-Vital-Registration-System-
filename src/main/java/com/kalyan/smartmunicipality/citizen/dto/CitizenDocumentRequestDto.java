package com.kalyan.smartmunicipality.citizen.dto;

import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CitizenDocumentRequestDto implements Serializable {
    private Long citizenId;
    private DocumentType documentType;
    //private byte[] fileData;  Citizen Gives Multipart file needs to convert
    private Long verifiedBy;
    private MultipartFile file;
    private LocalDate uploadDate=LocalDate.now();
    private LocalDate verifiedDate;
    private LocalDate createdAt=LocalDate.now();
    private LocalDate updatedAt;
}
