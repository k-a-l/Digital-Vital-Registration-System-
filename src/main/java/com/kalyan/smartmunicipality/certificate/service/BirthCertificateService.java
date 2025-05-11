package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificate;
import com.kalyan.smartmunicipality.certificate.repository.BirthCertificateRepository;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BirthCertificateService {
    private final BirthCertificateRepository birthCertificateRepository;
    private final CitizenRepository citizenRepository;

    public BirthCertificate saveCertificate(BirthCertificate birthCertificate) {
        // Check if the citizen object is not null first
        if (birthCertificate.getCitizenId() == null || birthCertificate.getCitizenId().getId() == null) {
            throw new RuntimeException("Citizen ID is missing in request");
        }

        Long citizenId = birthCertificate.getCitizenId().getId();

        Optional<Citizen> citizenOptional = citizenRepository.findById(citizenId);
        if (citizenOptional.isEmpty()) {
            throw new RuntimeException("Citizen Not Found");
        }

        Citizen citizen = citizenOptional.get();

        birthCertificate.setCitizenId(citizen);
        birthCertificate.setStatus(CertificateStatus.PENDING);
        birthCertificate.setRequestedBy(citizen.getId());

        return birthCertificateRepository.save(birthCertificate);
    }















}
