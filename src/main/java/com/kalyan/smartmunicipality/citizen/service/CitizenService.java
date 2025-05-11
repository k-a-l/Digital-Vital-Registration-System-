package com.kalyan.smartmunicipality.citizen.service;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.mapper.CitizenDtoMapper;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;
    public CitizenService(CitizenRepository citizenRepository){
        this.citizenRepository=citizenRepository;
    }

    public CitizenResponseDto createCitizen(CitizenRequestDto citizenRequestDto){
        Citizen citizen= CitizenDtoMapper.mapToEntity(citizenRequestDto);
        citizenRepository.save(citizen);
        return CitizenDtoMapper.mapToDto(citizen);

    }

    public List<CitizenResponseDto> getAllCitizens() {
        return citizenRepository.findAll()
                .stream()
                .map(CitizenDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }



}
