package com.kalyan.smartmunicipality.citizen.service;

import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenDocumentResponseDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.mapper.CitizenDocumentMapper;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.model.CitizenDocument;
import com.kalyan.smartmunicipality.citizen.repository.CitizenDocumentRepository;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

}
