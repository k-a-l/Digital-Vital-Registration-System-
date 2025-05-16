package com.kalyan.smartmunicipality.signature.controller;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.service.BirthCertificateService;
import com.kalyan.smartmunicipality.signature.service.DigitalSignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/v1/digital-signature")
@RequiredArgsConstructor
public class DigitalSignatureController {

    private final DigitalSignatureService digitalSignatureService;
    private final BirthCertificateService birthCertificateService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyDocumentSignature(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("referenceNumber") String referenceNumber) {
        if (file.isEmpty() || referenceNumber == null || referenceNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("File or reference number must not be empty.");
        }

        CertificateFile certificateFile = birthCertificateService.getCertificateByReferenceNumber(referenceNumber);
        if (certificateFile == null) {
            return ResponseEntity.badRequest().body("No certificate found for the given reference number.");
        }

        String storedSignature = certificateFile.getDigitalSignature();
        if (storedSignature == null || storedSignature.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("No digital signature found for this certificate.");
        }

        try {
            byte[] uploadedBytes = file.getBytes();  // ✅ raw binary, no UTF-8 decoding
            boolean isValid = digitalSignatureService.verifyCertificate(uploadedBytes, storedSignature);

            return isValid
                    ? ResponseEntity.ok("Certificate is valid and unmodified.")
                    : ResponseEntity.badRequest().body("Certificate is invalid or has been modified.");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error during signature verification: " + e.getMessage());
        }
    }

}
