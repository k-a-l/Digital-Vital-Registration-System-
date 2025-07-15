package com.kalyan.smartmunicipality.certificate.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.model.BirthCertificateRequest;
import com.kalyan.smartmunicipality.certificate.repository.BirthCertificateRepository;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import com.kalyan.smartmunicipality.email.service.EmailService;
import com.kalyan.smartmunicipality.notification.enums.DeliveryChannel;
import com.kalyan.smartmunicipality.notification.enums.NotificationEvent;
import com.kalyan.smartmunicipality.notification.enums.NotificationType;
import com.kalyan.smartmunicipality.notification.model.Notification;
import com.kalyan.smartmunicipality.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BirthCertificateService {
    private final BirthCertificateRepository birthCertificateRepository;
    private final CitizenRepository citizenRepository;
    private final BirthCertificateReportService birthCertificateReportService;
    private final CertificateFileRepository certificateFileRepository;
    private final NotificationService notificationService;
    private final EmailService emailService;


    public BirthCertificateRequest saveCertificate(BirthCertificateRequest birthCertificate) {

        boolean exists = birthCertificateRepository.existsByChildNameAndDateOfBirthAndCitizen(birthCertificate.getChildName(), birthCertificate.getDateOfBirth(), birthCertificate.getCitizen());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Certificate already exists for the given child name and date of birth");
        }
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
        birthCertificate.setMunicipality(birthCertificate.getMunicipality());
        birthCertificate.setDistrict(birthCertificate.getDistrict());
        birthCertificate.setNationality(birthCertificate.getNationality());

        BirthCertificateRequest savedRequest = birthCertificateRepository.save(birthCertificate);


        Notification.NotificationBuilder notification = Notification.builder()
                .event(NotificationEvent.REVIEWING)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(savedRequest.getCitizen().getUserEmail())
                .citizen(citizen)
                .message("Dear, "+ citizen.getFirstName() + "Your Birth Certificate has been successfully submitted and is under review.")
                .createdAt(LocalDateTime.now());


        notificationService.sendAndDispatch(notification.build());
        return savedRequest;
    }

    public CertificateFile getCertificateByReferenceNumber(String referenceNumber) {
        return certificateFileRepository.findByReferenceNumber(referenceNumber);
    }

    public CertificateFile generateBirthCertificateReport(Long id) {
        BirthCertificateRequest cert = birthCertificateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

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

        CertificateFile file = birthCertificateReportService.generateBirthCertificateReport(params, citizen, cert);

        cert.setCertificateFile(file);
        birthCertificateRepository.save(cert); // must save this relation

        params.put("referenceNumber", file.getReferenceNumber());

        return file;
    }

    public Long countBirthCertificateRequests(){
        return birthCertificateRepository.count();
    }

    public void deleteBirthCertificateRequestById(Long id){
         if(!birthCertificateRepository.existsById(id)){
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found with ID: " + id);

         }
         birthCertificateRepository.deleteById(id);
    }

    public Long countPendingRequest(){
        return birthCertificateRepository.findAll()
                .stream().filter(cert->cert.getStatus()==CertificateStatus.PENDING).count();
    }

    public Long countApprovedRequest(){
        return certificateFileRepository.count();
    }

    public List<BirthCertificateRequest> getAllRequests(){
        return birthCertificateRepository.findAll().stream().toList();
    }

    public Long countRejectedRequest(){
        return birthCertificateRepository.findAll()
                .stream()
                .filter(cert->cert.getStatus()==CertificateStatus.REJECTED)
                .count();
    }
    public List<BirthCertificateRequest> getRequestByCitizenId(Long id){
        return birthCertificateRepository.findByCitizenId(id);


    }
    public void approveBirthCertificateRequest(Long id) {
        birthCertificateRepository.findById(id).ifPresent(birthCertificate -> {
            birthCertificate.setStatus(CertificateStatus.APPROVED);
            BirthCertificateRequest savedRequest = birthCertificateRepository.save(birthCertificate);
            Citizen citizen = savedRequest.getCitizen();

            CertificateFile file = certificateFileRepository.findByBirthCertificateRequestId(id)
                    .orElseGet(() -> generateBirthCertificateReport(id));

           //File certificatePdf = new File(file.getFilePath());
           // log.warn("file Path {}", certificatePdf);// assume you store absolute path
            String to = savedRequest.getCitizen().getUserEmail();
            //String subject = "Municipality Notification - Birth Certificate Approved";
            String message = "Dear " + citizen.getFirstName() + ",\n\nYour birth certificate has been approved and is attached to this email.\n\nRegards,\nSmart Municipality";

           // emailService.sendEmailWithAttachment(to, subject, message, certificatePdf);

            // Optionally still log/send notification via Kafka
            Notification notification = Notification.builder()
                    .event(NotificationEvent.REVIEWING)
                    .channel(DeliveryChannel.BOTH)
                    .type(NotificationType.EMAIL)
                    .email(to)
                    .citizen(citizen)
                    .message(message)
                    .createdAt(LocalDateTime.now())
                    .certificate(file)
                    .certificateId(file.getId())
                    .build();

            notificationService.sendAndDispatch(notification);
        });
    }

    public void approveByVerifier(Long id) {
        birthCertificateRepository.findById(id).ifPresent(birthCertificate -> {
            birthCertificate.setStatus(CertificateStatus.APPROVED_BY_VERIFIER);
            birthCertificateRepository.save(birthCertificate);
        });
    }

    public void rejectBirthCertificateRequest(Long id) {
        birthCertificateRepository.findById(id).ifPresent(birthCertificate -> {
            birthCertificate.setStatus(CertificateStatus.REJECTED);
            birthCertificateRepository.save(birthCertificate);
        });
    }


    public BirthCertificateRequest getRequestById(Long id){
        return birthCertificateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found with ID: " + id));

    }

    public Long countBirthCertificateRequest(){
        return birthCertificateRepository.count();
    }

    public Map<String, Long> countBirthByMonth(){
        return birthCertificateRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(request -> request.getRequestedAt().getMonth().name(), Collectors.counting()));
    }

    public List<BirthCertificateRequest> getByStaffMunicipality(String municipality) {
        return birthCertificateRepository.findByMunicipality(municipality);
    }


}

















