package com.kalyan.smartmunicipality.certificate.controller;

import com.kalyan.smartmunicipality.certificate.model.BirthCertificate;
import com.kalyan.smartmunicipality.certificate.service.BirthCertificateService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class BirthCertificateController {
    private final BirthCertificateService birthCertificateService;

    @PostMapping("/save")
    public ResponseEntity<BirthCertificate> saveCertificate(@RequestBody BirthCertificate birthCertificate){
        BirthCertificate savedCertificate=birthCertificateService.saveCertificate(birthCertificate);
        return ResponseEntity.ok(savedCertificate);
    }

}
