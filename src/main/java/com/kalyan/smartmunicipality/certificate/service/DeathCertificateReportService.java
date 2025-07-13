package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.CertificateType;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.signature.service.SignatureKeysService;
import com.kalyan.smartmunicipality.signature.utils.SignatureUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeathCertificateReportService {
    private final CertificateFileRepository certificateFileRepository;
    private final SignatureKeysService signatureKeysService;

    @Transactional
    public CertificateFile generateDeathCertificateReport(Map<String, Object> parameters, Citizen citizen, DeathCertificateRequest request) {
        try {
            String referenceNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            parameters.put("referenceNumber", referenceNumber);
            parameters.put("signedMark", "This document bears a government-authorized digital signature, ensuring its authenticity and integrity.");
            parameters.put("tamperWarning", "Any modification to this document will be detected and deemed unauthorized. Tampering with official documents is a punishable offense under applicable laws.");

            InputStream reportStream = new ClassPathResource("/reports/deathCertificateReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            BigInteger signature = SignatureUtils.sign(pdfBytes, signatureKeysService.getPrivateKey(), signatureKeysService.getModulus());

            CertificateFile file = CertificateFile.builder()
                    .filePath("Death_certificate_" + request.getId() + ".pdf")
                    .deathCertificateRequest(request)
                    .citizen(citizen)
                    .fileData(pdfBytes)
                    .digitalSignature(signature.toString())
                    .publicKey(signatureKeysService.getPublicKey().toString())
                    .modulus(signatureKeysService.getModulus().toString())
                    .status(CertificateStatus.APPROVED)
                    .certificateType(CertificateType.DEATH)
                    .referenceNumber(referenceNumber)
                    .build();

            return certificateFileRepository.save(file);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }
    }
}
