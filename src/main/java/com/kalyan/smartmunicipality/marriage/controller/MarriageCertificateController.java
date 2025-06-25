package com.kalyan.smartmunicipality.marriage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.marriage.dto.MarriageCertificateResponseDto;
import com.kalyan.smartmunicipality.marriage.dto.MarriageCertificateReviewResponseDto;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import com.kalyan.smartmunicipality.marriage.service.MarriageCertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/marriage")
public class MarriageCertificateController {

    private final MarriageCertificateRequestService marriageCertificateRequestService;
    private final ObjectMapper objectMapper;
    private final CertificateFileRepository certificateFileRepository;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MarriageCertificateRequest> saveMarriageCertificateRequest(
            @RequestPart("request") String requestJson,
            @RequestPart(value = "marriagePhoto", required = true) MultipartFile marriagePhoto,
            @RequestPart(value = "wardOfficeFile", required = true) MultipartFile wardOfficeFile
    ) throws JsonProcessingException {
        MarriageCertificateRequest request = objectMapper.readValue(requestJson, MarriageCertificateRequest.class);
        MarriageCertificateRequest save = marriageCertificateRequestService.save(request, marriagePhoto, wardOfficeFile);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MarriageCertificateResponseDto>> getAllMarriageCertificateRequests() {
        return new ResponseEntity<>(marriageCertificateRequestService.getAllRequest(), HttpStatus.OK);
    }

    @GetMapping("{id}/by-id")
    private ResponseEntity<MarriageCertificateResponseDto> getMarriageCertificateRequestById(@PathVariable Long id) {
        return new ResponseEntity<>(marriageCertificateRequestService.getById(id),HttpStatus.OK);
    }

    @GetMapping("exist")
    private ResponseEntity<Boolean> existsByRequestedId(@RequestParam("requestedBy") Long id) {
        return new ResponseEntity<>(marriageCertificateRequestService.existsByRequestId(id),HttpStatus.OK);
    }

    @PatchMapping("/send-video-link/{id}")
    private ResponseEntity<Map<String, String>> verifyByVideoCall(@PathVariable("id") Long id) {
      marriageCertificateRequestService.sendVideoVerificationLink(id);
      return ResponseEntity.ok().body(Map.of("message", "verification link success"));

    }

    @PatchMapping("/{id}/approve")
    private ResponseEntity<Map<String, String>> approve(@PathVariable("id") Long id) {
        marriageCertificateRequestService.approve(id);
        generateMarriageCertificate(id);
        return ResponseEntity.ok().body(Map.of("message", "Approved success"));

    }

    @PatchMapping("/{id}/reject")
    private ResponseEntity<Map<String, String>> reject(@PathVariable("id") Long id) {
        marriageCertificateRequestService.reject(id);
        return ResponseEntity.ok().body(Map.of("message", "Rejected success"));
    }

    @GetMapping("/count-marriage")
    public ResponseEntity<Long> countMarriageCertificateRequests() {
        return new ResponseEntity<>(marriageCertificateRequestService.countMarriageCertificateRequests(), HttpStatus.OK);
    }

    @GetMapping("/by-request/{id}")
    public ResponseEntity<MarriageCertificateRequest> getMarriageCertificateById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(marriageCertificateRequestService.getRequestById(id), HttpStatus.OK);
    }
    @GetMapping("/review/{id}")
    public ResponseEntity<MarriageCertificateReviewResponseDto> getReview(@PathVariable Long id) {
        MarriageCertificateReviewResponseDto reviewDto = marriageCertificateRequestService.getReviewById(id);
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping("/{id}/generate")
    public ResponseEntity<byte[]> generateMarriageCertificate(@PathVariable Long id) {
        CertificateFile file = certificateFileRepository.findByMarriageCertificateRequestId(id)
                .orElseGet(()-> marriageCertificateRequestService.generateMarriageCertificate(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilePath())
                .contentType(MediaType.APPLICATION_PDF)
                .body(file.getFileData());

    }


}
