package com.kalyan.smartmunicipality.marriage.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.enums.CertificateStatus;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import com.kalyan.smartmunicipality.citizen.repository.CitizenDocumentRepository;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import com.kalyan.smartmunicipality.marriage.dto.*;
import com.kalyan.smartmunicipality.marriage.mapper.MarriageCertificateRequestMapper;
import com.kalyan.smartmunicipality.marriage.model.ForeignPerson;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import com.kalyan.smartmunicipality.marriage.repostory.ForeignPersonRepository;
import com.kalyan.smartmunicipality.marriage.repostory.MarriageCertificateRequestRepository;
import com.kalyan.smartmunicipality.notification.enums.DeliveryChannel;
import com.kalyan.smartmunicipality.notification.enums.NotificationEvent;
import com.kalyan.smartmunicipality.notification.enums.NotificationType;
import com.kalyan.smartmunicipality.notification.model.Notification;
import com.kalyan.smartmunicipality.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarriageCertificateRequestService {

    private final MarriageCertificateRequestRepository marriageCertificateRequestRepository;
    private final CitizenRepository citizenRepository;
    private final NotificationService notificationService;
    private final CertificateFileRepository certificateFileRepository;
    private final ForeignPersonRepository foreignPersonRepository;
    private final CitizenDocumentRepository citizenDocumentRepository;
    private final MarriageCertificateReportService marriageCertificateReportService;

    public MarriageCertificateRequest save(MarriageCertificateRequest request,
                                           MultipartFile marriagePhoto,
                                           MultipartFile wardOfficeFile) {
        try {
            if (marriagePhoto != null && !marriagePhoto.isEmpty()) {
                request.setMarriagePhotoData(Base64.getEncoder().encodeToString(marriagePhoto.getBytes()));
                request.setMarriagePhotoFileName(marriagePhoto.getOriginalFilename());
            }

            if (wardOfficeFile != null && !wardOfficeFile.isEmpty()) {
                request.setWardOfficeFileData(Base64.getEncoder().encodeToString(wardOfficeFile.getBytes()));
                request.setWardOfficeFileName(wardOfficeFile.getOriginalFilename());
            }

            return marriageCertificateRequestRepository.save(request);

        } catch (IOException e) {
            throw new RuntimeException("File processing failed: " + e.getMessage());
        }

    }

    public List<MarriageCertificateResponseDto> getAllRequest() {
        return marriageCertificateRequestRepository.findAll().stream().map(MarriageCertificateRequestMapper::toMarriageCertificateResponseDto).collect(Collectors.toList());
    }

    public MarriageCertificateResponseDto getById(Long id) {
        return marriageCertificateRequestRepository.findByRequestedById(id).stream().map(MarriageCertificateRequestMapper::toMarriageCertificateResponseDto).findFirst().orElse(null);
    }

    public boolean existsByRequestId(Long id) {
        return marriageCertificateRequestRepository.existsByRequestedById(id);
    }



    public void sendVideoVerificationLink(Long id) {
        MarriageCertificateRequest marriageCertificateRequest = marriageCertificateRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find marriage certificate request with id: " + id));

        // Generate the Jitsi meeting link
        String meetingLink = "https://meet.jit.si/marriagerequest-" + UUID.randomUUID();
        String scheduledTime = LocalDateTime.now().plusHours(24).format(DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a"));

        // Set status and meeting link
        marriageCertificateRequest.setStatus(CertificateStatus.PENDING_VIDEO_CALL_VERIFICATION);
        marriageCertificateRequest.setVideoVerificationLink(meetingLink);
        marriageCertificateRequest.setScheduledTime(scheduledTime);
        MarriageCertificateRequest savedRequest = marriageCertificateRequestRepository.save(marriageCertificateRequest);

        // Get citizen who requested
        Citizen citizen = citizenRepository.findById(marriageCertificateRequest.getRequestedBy().getId())
                .orElseThrow(() -> new RuntimeException("Could not find citizen with id: " + id));

        // Define a time (could be scheduled or current + 1 hour)

        // Compose the email message
        String emailMessage = String.format(
                "Dear %s,%s,\n\n" +
                        "Your marriage certificate request is under review. A video verification call has been scheduled.\n\n" +
                        "📅 Scheduled Time: %s\n" +
                        "🔗 Video Link: %s\n\n" +
                        "Please ensure both you and your partner are present with the following documents:\n" +
                        "1. Citizenship certificate\n" +
                        "2. Passport (if foreign partner)\n" +
                        "3. Ward office recommendation\n" +
                        "4. Marriage photo\n\n" +
                        "Please join the video call 5 minutes early. Ensure your camera and microphone are working.\n\n" +
                        "Best regards,\nSmart Municipality Services",
                citizen.getFirstName(), citizen.getLastName(),
                scheduledTime,
                meetingLink
        );

        // Build and send the notification
        Notification notification = Notification.builder()
                .event(NotificationEvent.REVIEWING)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(marriageCertificateRequest.getRequestedBy().getUserEmail())
                .citizen(citizen)
                .message(emailMessage)
                .createdAt(LocalDateTime.now())
                .build();

        notificationService.sendAndDispatch(notification);
    }


    public void approve(Long id) {
        MarriageCertificateRequest marriageCertificateRequest = marriageCertificateRequestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find marriage certificate request with id: " + id));

        marriageCertificateRequest.setStatus(CertificateStatus.APPROVED);
        marriageCertificateRequest.setVerifiedAt(LocalDate.now());
        MarriageCertificateRequest savedRequest = marriageCertificateRequestRepository.save(marriageCertificateRequest);

        CertificateFile file = certificateFileRepository.findByMarriageCertificateRequestId(id)
                .orElseGet(() -> generateMarriageCertificate(id));

        Citizen citizen = citizenRepository
                .findById(savedRequest.getRequestedBy().getId())
                .orElseThrow(() -> new RuntimeException("Could not find citizen with id: " + savedRequest.getRequestedBy().getId()));

        // Prepare email recipients
        List<String> recipients = new ArrayList<>();
        if (savedRequest.getRequestedBy().getUserEmail() != null) {
            recipients.add(savedRequest.getRequestedBy().getUserEmail());
        }

        if (savedRequest.getForeignPartner() != null && savedRequest.getForeignPartner().getEmail() != null) {
            recipients.add(savedRequest.getForeignPartner().getEmail());
        }

        // Prepare message and subject
        String subject = "🎉 Congratulations! Your Marriage Certificate Has Been Approved";
        String message = String.format(
                """
                         Congratulations! Your Marriage Certificate Has Been Approved\s
                        
                        Dear %s,
                        
                        We are pleased to inform you that your marriage certificate request has been approved.
                        You can find the approved certificate attached to this email.
                        
                        If you have any questions or need further assistance, feel free to contact your municipality office.
                        
                        Warm regards,
                        Smart Municipality Team""",
                citizen.getFirstName()
        );

        // Send email with attachment to all recipients (assuming your emailService supports multi-recipient sending)
       // emailService.sendEmailWithAttachment(recipients, subject, message, file.getFilePath());

        // Send notification to main citizen
        Notification notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_APPROVED)
                .channel(DeliveryChannel.BOTH)
                .type(NotificationType.EMAIL)
                .email(citizen.getUserEmail())
                .citizen(citizen)
                .message(message)
                .createdAt(LocalDateTime.now())
                .certificate(file)
                .certificateId(file.getId())
                .build();

        notificationService.sendAndDispatch(notification);
    }


    public void reject(Long id) {
    MarriageCertificateRequest marriageCertificateRequest = marriageCertificateRequestRepository.findById(id).orElseThrow(()-> new RuntimeException("Could not find marriage certificate request with id: " + id));
    marriageCertificateRequest.setStatus(CertificateStatus.REJECTED);
    marriageCertificateRequestRepository.save(marriageCertificateRequest);
    }

    public Long countMarriageCertificateRequests() {
        return marriageCertificateRequestRepository.count();
    }

    public Map<String, Long> countMarriageByMonth(){
        return marriageCertificateRequestRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(req -> req.getRequestedAt().getMonth().name(), Collectors.counting()));
    }

    public MarriageCertificateRequest getRequestById(Long id) {
        return marriageCertificateRequestRepository.findById(id).orElse(null);
    }


    public MarriageCertificateReviewResponseDto getReviewById(Long id) {
        MarriageCertificateRequest req = marriageCertificateRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marriage request not found"));

        // Determine partner: either husband or wife (Nepali)
        Citizen partner = req.getHusband() != null ? req.getHusband() : req.getWife();

        CitizenDto partnerDto = null;
        if (partner != null) {
            partnerDto = CitizenDto.builder()
                    .id(partner.getId())
                    .fullName(partner.getFirstName() + " " + partner.getMiddleName() + " " + partner.getLastName())
                    .gender(partner.getGender().name())
                    .citizenshipNumber(partner.getCitizenshipNumber())
                    .dateOfBirth(partner.getDateOfBirth())
                    .phone(String.valueOf(partner.getPhoneNo()))
                    .email(partner.getUserEmail()) // FIX: It's a field, not a getter
                    .build();
        }

        ForeignPartnerDto foreignDto = null;
        if (req.getForeignPartner() != null) {
            foreignDto = ForeignPartnerDto.builder()
                    .fullName(req.getForeignPartner().getFullName())
                    .nationality(req.getForeignPartner().getNationality())
                    .passportNumber(req.getForeignPartner().getPassportNumber())
                    .personCitizenshipNumber(req.getForeignPartner().getPersonCitizenshipNumber())
                    .email(req.getForeignPartner().getEmail())
                    .contactNumber(req.getForeignPartner().getContactNumber())
                    .dateOfBirth(req.getForeignPartner().getDateOfBirth())
                    .passportFile(FileDto.builder()
                            .fileName(req.getForeignPartner().getPassportFileName())
                            .fileData(req.getForeignPartner().getPassportFileData())
                            .build())
                    .photoFile(FileDto.builder()
                            .fileName(req.getForeignPartner().getPhotoFileName())
                            .fileData(req.getForeignPartner().getPhotoFileData())
                            .build())
                    .personCitizenshipFile(FileDto.builder()
                            .fileName(req.getForeignPartner().getPersonCitizenshipFileName())
                            .fileData(req.getForeignPartner().getPersonCitizenshipFileData())
                            .build())
                    .build();
        }

        return MarriageCertificateReviewResponseDto.builder()
                .id(req.getId())
                .requestedByFullName(req.getRequestedBy().getFirstName())
                .status(req.getStatus().name())
                .videoVerificationLink(req.getVideoVerificationLink())
                .placeOfMarriage(req.getPlaceOfMarriage())
                .marriageDate(req.getMarriageDate())
                .requestedAt(req.getRequestedAt())
                .verifiedAt(req.getVerifiedAt())
                .partner(partnerDto)
                .foreignPartner(foreignDto)
                .wardOfficeFile(FileDto.builder()
                        .fileName(req.getWardOfficeFileName())
                        .fileData(req.getWardOfficeFileData())
                        .build())
                .marriagePhoto(FileDto.builder()
                        .fileName(req.getMarriagePhotoFileName())
                        .fileData(req.getMarriagePhotoData())
                        .build())
                .build();
    }

    public CertificateFile generateMarriageCertificate(Long id) {
        if (id == null) throw new IllegalArgumentException("Marriage certificate request ID must not be null");

        MarriageCertificateRequest req = marriageCertificateRequestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Marriage request not found"));

        Map<String, Object> params = new HashMap<>();
        Citizen requestedBy = citizenRepository.findById(req.getRequestedBy().getId())
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        // Case 1: Mixed Marriage (Nepali + Foreign)
        if (req.getForeignPartner() != null) {
            ForeignPerson foreign = foreignPersonRepository.findById(req.getForeignPartner().getId())
                    .orElseThrow(() -> new RuntimeException("Foreign partner not found"));

            boolean isHusband = foreign.getGender() == Gender.MALE;

            // Get citizen photo
            List<CitizenDocument> citizenDocs = citizenDocumentRepository.findByCitizen_Id(requestedBy.getId());
            if (citizenDocs.isEmpty()) throw new RuntimeException("Citizen photo not found");
            InputStream citizenPhoto = new ByteArrayInputStream(Base64.getDecoder().decode(citizenDocs.getFirst().getFileData().getBytes()));

            // Decode foreign partner photo
            InputStream foreignPhoto = new ByteArrayInputStream(Base64.getDecoder().decode(foreign.getPhotoFileData().getBytes()));

            if (isHusband) {
                // Foreign husband, Nepali wife
                params.put("husbandName", foreign.getFullName());
                params.put("wifeName", requestedBy.getFirstName() + " " + requestedBy.getLastName());
                params.put("husbandPhoto", foreignPhoto);
                params.put("wifePhoto", citizenPhoto);
                params.put("foreignHusbandCitizenshipNumber", foreign.getPersonCitizenshipNumber());
                params.put("passportNumber", foreign.getPassportNumber());
                params.put("wifeCitizenshipNumber", foreign.getPersonCitizenshipNumber());

            } else {
                // Nepali husband, foreign wife
                params.put("husbandName", requestedBy.getFirstName() + " " + requestedBy.getLastName());
                params.put("wifeName", foreign.getFullName());
                params.put("husbandPhoto", citizenPhoto);
                params.put("wifePhoto", foreignPhoto);
                params.put("wifeCitizenshipNumber", foreign.getPersonCitizenshipNumber());
                params.put("passportNumber", foreign.getPassportNumber());
                params.put("husbandCitizenshipNumber", foreign.getPersonCitizenshipNumber());

            }


        }

        // Case 2: Both Nepali Citizens
        else if (req.getHusband() != null && req.getWife() != null) {
            Citizen husband = req.getHusband().getGender() == Gender.MALE ? req.getHusband() : req.getWife();
            Citizen wife = req.getHusband().getGender() == Gender.FEMALE ? req.getHusband() : req.getWife();

            List<CitizenDocument> husbandDocs = citizenDocumentRepository.findByCitizen_Id(husband.getId());
            List<CitizenDocument> wifeDocs = citizenDocumentRepository.findByCitizen_Id(wife.getId());

            if (husbandDocs.isEmpty() || wifeDocs.isEmpty())
                throw new RuntimeException("Husband or wife photo not found");

            InputStream husbandPhoto = new ByteArrayInputStream(Base64.getDecoder().decode(husbandDocs.getFirst().getFileData().getBytes()));
            InputStream wifePhoto = new ByteArrayInputStream(Base64.getDecoder().decode(wifeDocs.getFirst().getFileData().getBytes()));

            params.put("husbandName", husband.getFirstName() + " " + husband.getLastName());
            params.put("wifeName", wife.getFirstName() + " " + wife.getLastName());
            params.put("husbandPhoto", husbandPhoto);
            params.put("wifePhoto", wifePhoto);
            params.put("husbandCitizenshipNumber", husband.getCitizenshipNumber());
            params.put("wifeCitizenshipNumber", wife.getCitizenshipNumber());
        } else {
            throw new RuntimeException("Invalid marriage data: Cannot determine husband and wife.");
        }

        // Common params
        params.put("requestedBy", requestedBy.getFirstName() + " " + requestedBy.getLastName());
        params.put("marriageDate", req.getMarriageDate().toString());
        params.put("requestedAt", req.getRequestedAt().toString());
        params.put("verifiedAt", LocalDate.now().toString());
        params.put("issuedBy", requestedBy.getMunicipality());
        params.put("VerifiedBy", req.getVerifiedBy());

        // Generate certificate
        CertificateFile file = marriageCertificateReportService.generateMarriageCertificateReport(params, requestedBy, req);
        req.setCertificateFile(file);

        return file;
    }



}
