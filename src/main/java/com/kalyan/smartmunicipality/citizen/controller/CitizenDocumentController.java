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
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1/citizen/document")
public class CitizenDocumentController {
    private final DocumentService documentService;
    public CitizenDocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CitizenDocumentResponseDto> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("citizenId") Long citizenId,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "verifiedBy", required = false) Long verifiedBy
    ) throws Exception {
        CitizenDocumentRequestDto requestDto = CitizenDocumentRequestDto.builder()
                .citizenId(citizenId)
                .documentType(Enum.valueOf(DocumentType.class, documentType))
                .verifiedBy(verifiedBy)
                .file(file)
                .build();

        CitizenDocumentResponseDto responseDto = documentService.uploadDocument(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/list")
    public ResponseEntity<List<CitizenDocumentResponseDto>> getAllDocuments() {
        List<CitizenDocumentResponseDto> documents = documentService.getAllDocuments();
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }


    @PostMapping(value = "/multi-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<CitizenDocumentResponseDto>> uploadMultipleDocuments(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("citizenId") Long citizenId,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "verifiedBy", required = false) Long verifiedBy
    ) throws Exception {

        List<CitizenDocumentResponseDto> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            CitizenDocumentRequestDto dto = CitizenDocumentRequestDto.builder()
                    .citizenId(citizenId)
                    .documentType(DocumentType.valueOf(documentType))
                    .verifiedBy(verifiedBy)
                    .file(file)
                    .build();

            responses.add(documentService.uploadDocument(dto));
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/documentByCitizenId/{citizenId}")
    public ResponseEntity<List<CitizenDocumentResponseDto>> getDocumentByCitizenId(@PathVariable Long citizenId) {
        List<CitizenDocumentResponseDto> documents = documentService.getDocumentByCitizenId(citizenId);
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }



}
