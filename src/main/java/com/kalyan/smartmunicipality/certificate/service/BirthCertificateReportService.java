package com.kalyan.smartmunicipality.certificate.service;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class BirthCertificateReportService {
    public byte[] generateBirthCertificateReport(Map<String, Object> parameters){
        try{
            InputStream reportStream = new ClassPathResource("/reports/birthCertificateReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);

        }catch (Exception e){
            throw new RuntimeException("Error generating report",e);

        }
    }

}
