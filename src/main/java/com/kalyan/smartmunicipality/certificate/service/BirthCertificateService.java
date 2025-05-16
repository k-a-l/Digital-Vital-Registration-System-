package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.BirthCertificateRepository;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BirthCertificateService {
    private final BirthCertificateRepository birthCertificateRepository;
    private final CitizenRepository citizenRepository;
    private final BirthCertificateReportService birthCertificateReportService;
    private final CertificateFileRepository certificateFileRepository;


    public BirthCertificateRequest saveCertificate(BirthCertificateRequest birthCertificate) {
        if (birthCertificate.getCitizen() == null || birthCertificate.getCitizen().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citizen ID is missing in request");
        }

        Long citizenId = birthCertificate.getCitizen().getId();

        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen not found with ID: " + citizenId));

        birthCertificate.setCitizen(citizen);
        birthCertificate.setStatus(CertificateStatus.PENDING);
        birthCertificate.setRequestedBy(citizen.getId());
        birthCertificate.setRequestedAt(LocalDate.now());
        // ← add this if field exists

        return birthCertificateRepository.save(birthCertificate);
    }

    public CertificateFile getCertificateByReferenceNumber(String referenceNumber) {
        return certificateFileRepository.findByReferenceNumber(referenceNumber);
    }

    public byte[] generateBirthCertificateReport(Long id) {
        BirthCertificateRequest cert = birthCertificateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        cert.setStatus(CertificateStatus.APPROVED);
        Citizen citizen = cert.getCitizen();

        Map<String, Object> params = new HashMap<>();
        params.put("childName", cert.getChildName());
        params.put("gender", cert.getGender());
        params.put("dateOfBirth", cert.getDateOfBirth());
        params.put("birthPlace", citizen.getMunicipality());
        params.put("firstName", citizen.getFirstName());
        params.put("middleName", citizen.getMiddleName());
        params.put("lastName", citizen.getLastName());
        params.put("spouseName", citizen.getSpouseName());
        params.put("district", citizen.getDistrict());
        params.put("municipality", citizen.getMunicipality());
        params.put("wardNo", citizen.getWardNo());
        params.put("tole", citizen.getTole());
        params.put("nationality", citizen.getNationality());
        params.put("verifiedBy", "Ward Secretary");
        params.put("verifiedAt", citizen.getMunicipality());
        params.put("issuedDate", LocalDate.now());

        // 👇 Generate and save the CertificateFile
        CertificateFile file = birthCertificateReportService.generateBirthCertificateReport(params, citizen, cert);

        // 👇 Set the generated file to the request
        cert.setCertificateFile(file);
        birthCertificateRepository.save(cert); // must save this relation

        // 👇 Now you can safely get the reference number
        params.put("referenceNumber", file.getReferenceNumber());

        return file.getFileData(); // Return the PDF data
    }

}

















