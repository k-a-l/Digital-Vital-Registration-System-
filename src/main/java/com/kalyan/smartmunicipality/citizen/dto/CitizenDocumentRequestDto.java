package com.kalyan.smartmunicipality.citizen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CitizenDocumentRequestDto {
    private Long citizenId;
    private String documentType;
    //private byte[] fileData;  Citizen Gives Multipart file needs to convert
    private String fileName;
    private MultipartFile file;
}
