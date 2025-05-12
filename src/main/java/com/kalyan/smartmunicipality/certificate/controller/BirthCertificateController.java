package com.kalyan.smartmunicipality.certificate.controller;

import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.service.BirthCertificateReportService;
import com.kalyan.smartmunicipality.certificate.service.BirthCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class BirthCertificateController {
    private final BirthCertificateService birthCertificateService;
    private final BirthCertificateReportService birthCertificateReportService;


    @PostMapping("/save")
    public ResponseEntity<BirthCertificateRequest> saveCertificate(@RequestBody BirthCertificateRequest birthCertificate){
        BirthCertificateRequest savedCertificate=birthCertificateService.saveCertificate(birthCertificate);
        return ResponseEntity.ok(savedCertificate);
    }

    @GetMapping("/birth/{id}/download")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        byte[] pdf = birthCertificateService.generateBirthCertificateReport(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=birth_certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }




}
