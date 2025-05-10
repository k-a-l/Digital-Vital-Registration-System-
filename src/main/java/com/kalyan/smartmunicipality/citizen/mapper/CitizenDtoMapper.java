package com.kalyan.smartmunicipality.citizen.mapper;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.model.Citizen;

public class CitizenDtoMapper {
    public static CitizenResponseDto mapToDto(Citizen citizen){
        return CitizenResponseDto.builder()
                .id(citizen.getId())
                .firstName(citizen.getFirstName())
                .middleName(citizen.getMiddleName())
                .lastName(citizen.getLastName())
                .dateOfBirth(citizen.getDateOfBirth())
                .phoneNo(citizen.getPhoneNo())
                .fatherName(citizen.getFatherName())
                .motherName(citizen.getMotherName())
                .grandfatherName(citizen.getGrandfatherName())
                .grandmotherName(citizen.getGrandmotherName())
                .nationality(citizen.getNationality())
                .district(citizen.getDistrict())
                .wardNo(citizen.getWardNo())
                .gender(citizen.getGender())
                .tole(citizen.getTole())
                .verifiedBy(citizen.getVerifiedBy())
                .verifiedDate(citizen.getVerifiedDate())
                .createdAt(citizen.getCreatedAt())

                .build();

    }
    public static Citizen mapToEntity(CitizenRequestDto dto){
        return Citizen.builder()

                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .fatherName(dto.getFatherName())
                .phoneNo(dto.getPhoneNo())
                .motherName(dto.getMotherName())
                .grandfatherName(dto.getGrandfatherName())
                .nationality(dto.getNationality())
                .district(dto.getDistrict())
                .wardNo(dto.getWardNo())
                .municipality(dto.getMunicipality())
                .tole(dto.getTole())
                .gender(dto.getGender())
                .grandmotherName(dto.getGrandmotherName())
                .grandfatherName(dto.getGrandfatherName())
                .createdAt(dto.getCreatedAt())
                .verifiedBy(dto.getVerifiedBy())
                .updatedAt(dto.getUpdatedAt())
                .verifiedDate(dto.getVerifiedDate())
                .isVerified(dto.isVerified())
                .build();
    }

}

