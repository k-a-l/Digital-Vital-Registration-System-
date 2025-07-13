package com.kalyan.smartmunicipality.citizen.mapper;

import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentResponseDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

public class CitizenDocumentMapper {

        public static CitizenDocumentResponseDto toResponseDto(CitizenDocument document) {
            return CitizenDocumentResponseDto.builder()
                    .id(document.getId())
                    .fileName(document.getFileName())
                    .documentType(document.getDocumentType())
                    .updatedAt(document.getUploadDate())
                    .citizenId(document.getCitizen().getId())
                    .verifiedBy(document.getVerifiedBy())
                    .verifiedDate(document.getVerifiedDate())
                    .createdAt(document.getCreatedAt())
                    .fileData(document.getFileData())
                    .build();
        }

    public static CitizenDocument mapToEntity(CitizenDocumentRequestDto dto, Citizen citizen) throws IOException {
        return CitizenDocument.builder()
                .citizen(citizen)
                .documentType(dto.getDocumentType())
                .fileData(Base64.getEncoder().encodeToString(dto.getFile().getBytes()))
                .fileName(dto.getFile().getOriginalFilename())
                .uploadDate(LocalDate.now())
                .verifiedBy(dto.getVerifiedBy())
                .verifiedDate(dto.getVerifiedDate())
                .createdAt(LocalDate.now())
                //.updatedAt(LocalDate.now())

                .deathCertificateRequest(dto.getDeathCertificateRequestId())
                .build();
    }

    public static CitizenDocument updateDocument(CitizenDocumentRequestDto dto, Citizen citizen) throws IOException {
        return CitizenDocument.builder()
                .citizen(citizen)
                .documentType(dto.getDocumentType())
                .fileData(Base64.getEncoder().encodeToString(dto.getFile().getBytes()))
                .fileName(dto.getFile().getOriginalFilename())
                .uploadDate(citizen.getCreatedAt())
                .verifiedBy(dto.getVerifiedBy())
                .verifiedDate(dto.getVerifiedDate())
                .createdAt(citizen.getCreatedAt())
                .updatedAt(LocalDate.now())

                .deathCertificateRequest(dto.getDeathCertificateRequestId())
                .build();
    }
}


