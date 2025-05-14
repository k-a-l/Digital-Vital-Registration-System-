package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
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
        params.put("birthPlace", cert.getCitizen().getMunicipality());

        params.put("firstName", cert.getCitizen().getFirstName());
        params.put("middleName", cert.getCitizen().getMiddleName());
        params.put("lastName", cert.getCitizen().getLastName());
        params.put("spouseName", cert.getCitizen().getSpouseName());

        params.put("district", cert.getCitizen().getDistrict());
        params.put("municipality", cert.getCitizen().getMunicipality());
        params.put("wardNo", cert.getCitizen().getWardNo());
        params.put("tole", cert.getCitizen().getTole());
        params.put("nationality", cert.getCitizen().getNationality());

        params.put("verifiedBy", "Ward Secretary"); // or dynamic
        params.put("verifiedAt", cert.getCitizen().getMunicipality());
        params.put("issuedDate", LocalDate.now());


        Citizen citizen = cert.getCitizen();


        return birthCertificateReportService.generateBirthCertificateReport(params,citizen,cert);

    }
















}
