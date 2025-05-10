package com.kalyan.smartmunicipality.citizen.controller;

import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import com.kalyan.smartmunicipality.citizen.service.DocumentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api/v1/citizen/document")
public class CitizenDocumentController {
    private final DocumentService documentService;
    public CitizenDocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CitizenDocumentResponseDto> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestPart("citizenId") Long citizenId,
            @RequestPart("documentType") String documentType,
            @RequestPart(value = "verifiedBy", required = false) Long verifiedBy
    ) throws Exception {

        // Build DTO manually from multipart data
        CitizenDocumentRequestDto requestDto = CitizenDocumentRequestDto.builder()
                .citizenId(citizenId)
                .documentType(Enum.valueOf(DocumentType.class, documentType))
                .verifiedBy(verifiedBy)
                .file(file)
                .build();

        CitizenDocumentResponseDto responseDto = documentService.uploadDocument(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
