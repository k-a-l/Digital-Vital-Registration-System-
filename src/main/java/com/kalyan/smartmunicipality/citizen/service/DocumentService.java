package com.kalyan.smartmunicipality.citizen.service;

import com.kalyan.smartmunicipality.certificate.certificateFile.CertificateFile;
import com.kalyan.smartmunicipality.certificate.repository.CertificateFileRepository;
import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import com.kalyan.smartmunicipality.citizen.mapper.CitizenDocumentMapper;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import com.kalyan.smartmunicipality.citizen.repository.CitizenDocumentRepository;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import com.kalyan.smartmunicipality.notification.enums.DeliveryChannel;
import com.kalyan.smartmunicipality.notification.enums.NotificationEvent;
import com.kalyan.smartmunicipality.notification.enums.NotificationType;
import com.kalyan.smartmunicipality.notification.model.Notification;
import com.kalyan.smartmunicipality.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {
     private final CitizenDocumentRepository citizenDocumentRepository;
     private final CitizenRepository citizenRepository;
     private final CertificateFileRepository certificateFileRepository;
     private final NotificationService notificationService;




     public CitizenDocumentResponseDto uploadDocument(CitizenDocumentRequestDto citizenDocumentRequestDto) throws IOException {
         Citizen citizen=citizenRepository.findById(citizenDocumentRequestDto.getCitizenId())
                 .orElseThrow(()->new RuntimeException("Citizen Not Found"));
         CitizenDocument citizenDocument= CitizenDocumentMapper.mapToEntity(citizenDocumentRequestDto,citizen);
         CitizenDocument savedDocument = citizenDocumentRepository.save(citizenDocument);

         Optional<CertificateFile> certificateFileOptional = certificateFileRepository.findById(savedDocument.getCitizen().getId());
         String htmlMessage = String.format("""
<div style="background-color: #f0fdf4; border: 1px solid #34d399; padding: 24px; border-radius: 12px; font-family: Arial, sans-serif; max-width: 600px; color: #064e3b;">
  <div style="display: flex; align-items: center; margin-bottom: 16px;">
    <span style="font-size: 28px; color: #10b981; margin-right: 12px;">✔️</span>
    <h2 style="margin: 0; font-size: 22px; font-weight: bold;">Digital Vital Registration</h2>
  </div>
  <div style="font-size: 16px; line-height: 1.6;">
    <p>Dear <strong>%s</strong>, 👋</p>
    <p>Your citizen profile has been <strong>successfully submitted</strong> and is currently <strong>under review</strong>.</p>
    <p style="margin-top: 12px; font-size: 14px; color: #065f46;">
      🕒 <strong>Submitted at:</strong> %s
    </p>
  </div>
</div>
""",
                 citizen.getFirstName(),
                 LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a")));

         Notification.NotificationBuilder notification = Notification.builder()
                 .event(NotificationEvent.REVIEWING)
                 .channel(DeliveryChannel.EMAIL)
                 .type(NotificationType.EMAIL)
                 .email(savedDocument.getCitizen().getUserEmail())
                 .citizen(citizen)
                 .message(htmlMessage)
                 .createdAt(LocalDateTime.now());
         certificateFileOptional.ifPresent(notification::certificate);


         notificationService.sendAndDispatch(notification.build());
         //log.warn("Citizen created: {}", savedDocument.getCitizen().getId());
         return CitizenDocumentMapper.toResponseDto(savedDocument);

     }

     public List<CitizenDocumentResponseDto> getAllDocuments(){
         List<CitizenDocument> documents=citizenDocumentRepository.findAll();
         return documents.stream().map(CitizenDocumentMapper::toResponseDto).collect(Collectors.toList());

     }


    public List<CitizenDocumentResponseDto> uploadMultipleDocuments(
            MultipartFile[] files, Long citizenId, DocumentType documentType, Long verifiedBy) throws IOException {

        List<CitizenDocumentResponseDto> responseList = new ArrayList<>();

        for (MultipartFile file : files) {
            CitizenDocumentRequestDto dto = CitizenDocumentRequestDto.builder()
                    .citizenId(citizenId)
                    .documentType(documentType)
                    .verifiedBy(verifiedBy)
                    .file(file)
                    .build();

            CitizenDocumentResponseDto response = uploadDocument(dto); // reuse existing logic
            responseList.add(response);
        }

        return responseList;
    }

    public List<CitizenDocumentResponseDto> getDocumentByCitizenId(Long citizenId){

        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Unable to find the citizen"));
        List<CitizenDocument> documents = citizenDocumentRepository.findByCitizen_Id(citizenId);

        return documents.stream().map(CitizenDocumentMapper::toResponseDto).collect(Collectors.toList());




    }

}
