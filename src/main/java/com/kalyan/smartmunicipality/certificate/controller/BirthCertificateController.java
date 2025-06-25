package com.kalyan.smartmunicipality.certificate.controller;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.certificate.service.BirthCertificateReportService;
import com.kalyan.smartmunicipality.certificate.service.BirthCertificateService;
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
@RequestMapping("/api/v1/certificate")
public class BirthCertificateController {
    private final BirthCertificateService birthCertificateService;
    private final BirthCertificateReportService birthCertificateReportService;
    private final CertificateFileRepository certificateFileRepository;


    @PostMapping("/save")
    public ResponseEntity<BirthCertificateRequest> saveCertificate(@RequestBody BirthCertificateRequest birthCertificate){
        BirthCertificateRequest savedCertificate=birthCertificateService.saveCertificate(birthCertificate);
        return ResponseEntity.ok(savedCertificate);
    }

    @GetMapping("/birth/{id}/generate")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        CertificateFile file = certificateFileRepository.findByBirthCertificateRequestId(id)
                .orElseGet(() -> birthCertificateService.generateBirthCertificateReport(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilePath())
                .contentType(MediaType.APPLICATION_PDF)
                .body(file.getFileData());
    }


    @GetMapping("/download/{referenceNumber}")
    public ResponseEntity<?> downloadCertificateByReferenceNumber(@PathVariable String referenceNumber) {
        if (referenceNumber == null || referenceNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Reference number must not be empty.");
        }

        CertificateFile certificateFile = certificateFileRepository.findByReferenceNumber(referenceNumber);
        if (certificateFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificate not found for the given reference number.");
        }

        byte[] pdf = certificateFile.getFileData();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + certificateFile.getFilePath())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countBirthCertificateRequests(){
        return ResponseEntity.ok(birthCertificateService.countBirthCertificateRequests());
    }

    @GetMapping("/pending-count")
    public ResponseEntity<Long> countPendingRequest(){
        return ResponseEntity.ok(birthCertificateService.countPendingRequest());
    }

    @GetMapping("/approved-count")
    public ResponseEntity<Long> countApprovedRequest(){
        return ResponseEntity.ok(birthCertificateService.countApprovedRequest());
    }
    @GetMapping("/rejected-count")
    public ResponseEntity<Long> countRejectedRequest(){
        return ResponseEntity.ok(birthCertificateService.countRejectedRequest());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBirthCertificateRequestById(@PathVariable Long id){
        birthCertificateService.deleteBirthCertificateRequestById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/list")
    public ResponseEntity<Iterable<BirthCertificateRequest>> getAllRequests(){
        return ResponseEntity.ok(birthCertificateService.getAllRequests());
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<BirthCertificateRequest>> getRequestsByCitizenId(@PathVariable Long citizenId) {
        return ResponseEntity.ok(birthCertificateService.getRequestByCitizenId(citizenId));
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Map<String, String>> approveBirthCertificate(@PathVariable Long id){
        birthCertificateService.approveBirthCertificateRequest(id);
        return ResponseEntity.ok(Map.of("message","Approved"));
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Map<String,String>> rejectBirthCertificate(@PathVariable Long id){
        birthCertificateService.rejectBirthCertificateRequest(id);
        return ResponseEntity.ok(Map.of("message","Rejected"));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<BirthCertificateRequest> getBirthCertificateById(@PathVariable Long id){
        BirthCertificateRequest req = birthCertificateService.getRequestById(id);
        return ResponseEntity.ok(req);
    }


    @GetMapping("/count-birth")
    public ResponseEntity<Long> countBirthCertificate(){
        return new ResponseEntity<>(birthCertificateReportService.countGeneratedBirthCertificate(), HttpStatus.OK);
    }







}
