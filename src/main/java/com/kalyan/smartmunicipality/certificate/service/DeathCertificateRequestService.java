package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.model.DeathCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.DeathCertificateRepository;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeathCertificateRequestService {
    private final CitizenRepository citizenRepository;
    private final DeathCertificateRepository deathCertificateRepository;
    private final DeathCertificateReportService deathCertificateReportService;

    public DeathCertificateRequest saveDeathCertificateRequest(DeathCertificateRequest deathCertificateRequest) {
        if(deathCertificateRepository.existsByDeceasedNameAndDateOfBirthAndDeceasedDateAndRequestedBy(deathCertificateRequest.getDeceasedName(),deathCertificateRequest.getDateOfBirth(),
                deathCertificateRequest.getDeceasedDate(),deathCertificateRequest.getRequestedBy())){
            throw new RuntimeException("Death certificate already exists");
        }

        Long citizenId = deathCertificateRequest.getRequestedBy().getId();
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen not found with ID: " + citizenId));
        deathCertificateRequest.setRequestedBy(citizen);
        deathCertificateRequest.setCertificateStatus(CertificateStatus.PENDING);
        deathCertificateRequest.setRequestedAt(LocalDateTime.now());


        return deathCertificateRepository.save(deathCertificateRequest);
    }

    public CertificateFile generateDeathCertificateFile(Long id){
        DeathCertificateRequest request = deathCertificateRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Death certificate not found"));
        Citizen citizen = request.getRequestedBy();

        int ageAtDeath = request.getDeceasedDate().getYear() - request.getDateOfBirth().getYear();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fullName", request.getDeceasedName());
        parameters.put("dateOfBirth", request.getDateOfBirth());
        parameters.put("dateOfDeath", request.getDeceasedDate());
        parameters.put("ageAtDeath", ageAtDeath);
        parameters.put("requestedBy", citizen.getFirstName() + " " + citizen.getMiddleName() + " " + citizen.getLastName());
        parameters.put("relation", request.getRelation().name());
        parameters.put("fatherName", request.getFatherName());
        parameters.put("motherName", request.getMotherName());
        parameters.put("gender", request.getGender().name());
        parameters.put("verifiedBy", "Ram Kumar");
        parameters.put("verifiedAt", LocalDateTime.now().toString());
        parameters.put("issuedDate", LocalDate.now());

        CertificateFile file = deathCertificateReportService.generateDeathCertificateReport(parameters,citizen,request);

        request.setCertificateFile(file);

        parameters.put("referenceNumber", file.getReferenceNumber());
        return file;




    }

    public List<DeathCertificateRequest> getAllRequests(){
        return deathCertificateRepository.findAll();

    }

    public List<DeathCertificateRequest> getAllRequestsByCitizenId(Long id){
        return deathCertificateRepository.findByRequestedById(id);
    }

    public void approveDeathCertificateRequest(Long id){
        deathCertificateRepository.findById(id)
                .ifPresent(deathCertificate -> {deathCertificate.setCertificateStatus(CertificateStatus.APPROVED);
                deathCertificateRepository.save(deathCertificate);});
    }

    public void rejectDeathCertificateRequest(Long id){
        deathCertificateRepository.findById(id)
                .ifPresent(deathCertificate -> {deathCertificate.setCertificateStatus(CertificateStatus.REJECTED);
                deathCertificateRepository.save(deathCertificate);
                });
    }

    public Long countDeathCertificateRequest(){
        return deathCertificateRepository.count();
    }

    public Map<String, Long> countDeathByMonth(){
        return deathCertificateRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(req -> req.getRequestedAt().getMonth().name(), Collectors.counting()));
    }

}
