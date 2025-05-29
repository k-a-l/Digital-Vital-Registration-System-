package com.kalyan.smartmunicipality.citizen.service;

import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentResponseDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.DocumentType;
import com.kalyan.smartmunicipality.citizen.mapper.CitizenDocumentMapper;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import com.kalyan.smartmunicipality.citizen.repository.CitizenDocumentRepository;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DocumentService {
     private final CitizenDocumentRepository citizenDocumentRepository;
     private final CitizenRepository citizenRepository;


     public CitizenDocumentResponseDto uploadDocument(CitizenDocumentRequestDto citizenDocumentRequestDto) throws IOException {
         Citizen citizen=citizenRepository.findById(citizenDocumentRequestDto.getCitizenId())
                 .orElseThrow(()->new RuntimeException("Citizen Not Found"));
         CitizenDocument citizenDocument= CitizenDocumentMapper.mapToEntity(citizenDocumentRequestDto,citizen);
         citizenDocumentRepository.save(citizenDocument);
         return CitizenDocumentMapper.toResponseDto(citizenDocument);
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
