package com.kalyan.smartmunicipality.certificate.controller;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.certificate.service.DeathCertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/death-certificate")
public class DeathCertificateController {
    private final DeathCertificateRequestService deathCertificateRequestService;
    private final CertificateFileRepository certificateFileRepository;

    @PostMapping("/save")
    public ResponseEntity<DeathCertificateRequest> createDeathCertificateRequest(
            @RequestBody DeathCertificateRequest request) {
        DeathCertificateRequest savedRequest = deathCertificateRequestService.saveDeathCertificateRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    @PostMapping("/death/{id}/generate")
    public ResponseEntity<byte[]> generateDeathCertificate(@PathVariable long id) {
        CertificateFile file = certificateFileRepository.findByDeathCertificateRequestId(id)
                .orElseGet(()->deathCertificateRequestService.generateDeathCertificateFile(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.APPLICATION_PDF)
                .body(file.getFileData());
    }

    @GetMapping("/list")
    public ResponseEntity<List<DeathCertificateRequest>> getAllDeathCertificateRequests() {
        List<DeathCertificateRequest> list= deathCertificateRequestService.getAllRequests();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}/list")
    public ResponseEntity<List<DeathCertificateRequest>> getAllDeathCertificateRequests(@PathVariable long id) {
        List<DeathCertificateRequest> list= deathCertificateRequestService.getAllRequestsByCitizenId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PatchMapping("{id}/approve")
    public ResponseEntity<Map<String,String>> approveDeathCertificate(@PathVariable long id) {
             deathCertificateRequestService.approveDeathCertificateRequest(id);
             return ResponseEntity.ok(Map.of("status", "approved"));


    }
    @PatchMapping("{id}/reject")
    public ResponseEntity<Map<String,String>> updateDeathCertificateRequest(@PathVariable long id) {
        deathCertificateRequestService.rejectDeathCertificateRequest(id);
        return ResponseEntity.ok(Map.of("status", "rejected"));
    }

    @GetMapping("/count-death")
    public ResponseEntity<Long> countDeathCertificateRequests() {
        return ResponseEntity.ok().body(deathCertificateRequestService.countDeathCertificateRequest());
    }
}
