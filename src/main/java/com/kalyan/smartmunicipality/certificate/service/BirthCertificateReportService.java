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

import java.io.InputStream;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BirthCertificateReportService {
    private final CertificateFileRepository certificateFileRepository;
    public byte[] generateBirthCertificateReport(Map<String, Object> parameters, Citizen citizen, BirthCertificateRequest request){
        try{
            InputStream reportStream = new ClassPathResource("/reports/birthCertificateReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            CertificateFile file = CertificateFile.builder()
                    .filePath("birth_certificate_" + request.getId() +".pdf")
                    .birthCertificateRequest(request)
                    .citizenId(citizen)
                    .fileData(pdfBytes)
                    .status(CertificateStatus.APPROVED)
                    .certificateType(CertificateType.BIRTH)
                    .build();
            certificateFileRepository.save(file);
            return pdfBytes;


        }catch (Exception e){
            throw new RuntimeException("Error generating report",e);

        }
    }

}
