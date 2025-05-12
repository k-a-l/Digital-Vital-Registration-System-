package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.BirthCertificateRepository;
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

@RequiredArgsConstructor
@Service
public class BirthCertificateService {
    private final BirthCertificateRepository birthCertificateRepository;
    private final CitizenRepository citizenRepository;
    private final BirthCertificateReportService birthCertificateReportService;


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
        birthCertificate.setRequestedAt(LocalDate.now()); // ← add this if field exists

        return birthCertificateRepository.save(birthCertificate);
    }

    public byte[] generateBirthCertificateReport(Long id){
        BirthCertificateRequest cert = birthCertificateRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Certificate not found"));
        cert.setStatus(CertificateStatus.APPROVED); //check
        if(cert.getStatus()!=CertificateStatus.APPROVED){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Certificate not approved yet");

        }
        Map<String, Object> params = new HashMap<>();
        params.put("childName", cert.getChildName());
        params.put("gender", cert.getGender());
        params.put("dateOfBirth", cert.getDateOfBirth());
        params.put("birthPlace", cert.getCitizen().getMunicipality()); // Adjust as needed
        params.put("fatherName", cert.getCitizen().getFatherName());
        params.put("motherName", cert.getCitizen().getMotherName());
        params.put("grandFatherName", cert.getCitizen().getGrandfatherName());
        params.put("verifiedBy", "Ward Secretary"); // Replace with actual data
        params.put("verifiedAt", "Tokha Municipality"); // Replace with actual data
        params.put("issuedDate", LocalDate.now());

        Citizen citizen = cert.getCitizen();
        return birthCertificateReportService.generateBirthCertificateReport(params);

    }
















}
