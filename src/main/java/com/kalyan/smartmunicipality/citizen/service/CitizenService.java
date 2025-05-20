package com.kalyan.smartmunicipality.citizen.service;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.mapper.CitizenDtoMapper;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
public Long getCitizenCount(){
        return citizenRepository.count();
}

public void deleteCitizenById(Long id){
        citizenRepository.deleteById(id);
}

    public CitizenResponseDto updateCitizen(Long id, CitizenRequestDto dto) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        // Map updated fields from DTO into the entity
        CitizenDtoMapper.updateEntityFromDto(citizen, dto);

        // Save the updated entity
        Citizen updated = citizenRepository.save(citizen);

        // Return updated DTO
        return CitizenDtoMapper.mapToDto(updated);
    }

    public Long getMaleCount() {
        return citizenRepository.countByGender(Gender.MALE);
    }

    public Long getFemaleCount() {
        return citizenRepository.countByGender(Gender.FEMALE);
    }

    public Long getOthersCount(){
return citizenRepository.countByGender(Gender.OTHERS);
    }




}
