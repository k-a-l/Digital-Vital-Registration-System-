package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.enums.CertificateType;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BirthCertificateReportService {
    private final CertificateFileRepository certificateFileRepository;
    public byte[] generateBirthCertificateReport(Map<String, Object> parameters){
        try{
            InputStream reportStream = new ClassPathResource("/reports/birthCertificateReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);

        }catch (Exception e){
            throw new RuntimeException("Error generating report",e);

        }

    /*public File generateBirthCertificateReport(Map<String, Object> parameters, Long requestId, Citizen citizen) {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/BirthCertificateReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Generate file name and path
            String fileName = "birth_certificate_" + requestId + "_" + System.currentTimeMillis() + ".pdf";
            String filePath = "src/main/resources/static/certificates/" + fileName;
            File file = new File(filePath);

            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            // Save CertificateFile entry
            CertificateFile certificateFile = CertificateFile.builder()
                    .fileName(fileName)
                    .filePath(file.getAbsolutePath())
                    .birthCertificateRequest(BirthCertificateRequest.builder().id(requestId).build())
                    .citizenId(citizen)
                    .verifiedBy(parameters.get("verifiedBy") != null ? Long.parseLong(parameters.get("verifiedBy").toString()) : null)
                    .verifiedAt(LocalDate.now())
                    .certificateType(CertificateType.BIRTH)
                    .status(CertificateStatus.APPROVED)
                    .build();

            certificateFileRepository.save(certificateFile);
            return file;

        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }*/
    }


}
