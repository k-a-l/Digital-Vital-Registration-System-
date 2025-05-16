package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.CertificateType;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
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

@RequiredArgsConstructor
@Service
public class BirthCertificateReportService {
    private final CertificateFileRepository certificateFileRepository;
    private final SignatureKeysService signatureKeysService;

    @Transactional
    public CertificateFile generateBirthCertificateReport(Map<String, Object> parameters, Citizen citizen, BirthCertificateRequest request) {
        try {
            // ✅ Generate reference number first
            String referenceNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // ✅ Add to Jasper parameters BEFORE filling the report
            parameters.put("referenceNumber", referenceNumber);

            InputStream reportStream = new ClassPathResource("/reports/birthCertificateReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // ✅ Now use the same referenceNumber in the entity
            BigInteger signature = SignatureUtils.sign(pdfBytes, signatureKeysService.getPrivateKey(), signatureKeysService.getModulus());

            CertificateFile file = CertificateFile.builder()
                    .filePath("birth_certificate_" + request.getId() + ".pdf")
                    .birthCertificateRequest(request)
                    .citizenId(citizen)
                    .fileData(pdfBytes)
                    .digitalSignature(signature.toString())
                    .publicKey(signatureKeysService.getPublicKey().toString())
                    .modulus(signatureKeysService.getModulus().toString())
                    .status(CertificateStatus.APPROVED)
                    .certificateType(CertificateType.BIRTH)
                    .referenceNumber(referenceNumber) // ✅ Set here
                    .build();

            return certificateFileRepository.save(file);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }
    }


}
