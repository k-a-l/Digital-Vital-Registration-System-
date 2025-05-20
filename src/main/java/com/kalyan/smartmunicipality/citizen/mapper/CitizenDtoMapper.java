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
                .spouseName(citizen.getSpouseName())
                .verifiedBy(citizen.getVerifiedBy())
                .verifiedDate(citizen.getVerifiedDate())
                .createdAt(citizen.getCreatedAt())

                .build();

    }
    public static Citizen mapToEntity(CitizenRequestDto dto) {
        return Citizen.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .spouseName(dto.getSpouseName())
                .fatherName(dto.getFatherName())
                .motherName(dto.getMotherName())
                .grandfatherName(dto.getGrandfatherName())
                .grandmotherName(dto.getGrandmotherName())
                .nationality(dto.getNationality())
                .district(dto.getDistrict())
                .wardNo(dto.getWardNo())
                .municipality(dto.getMunicipality())
                .tole(dto.getTole())
                .phoneNo(dto.getPhoneNo())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .verifiedBy(dto.getVerifiedBy())
                .verifiedDate(dto.getVerifiedDate())
                .isVerified(dto.isVerified())
                .build();
    }

    public static void updateEntityFromDto(Citizen citizen, CitizenRequestDto dto) {
        citizen.setFirstName(dto.getFirstName());
        citizen.setMiddleName(dto.getMiddleName());
        citizen.setLastName(dto.getLastName());
        citizen.setDateOfBirth(dto.getDateOfBirth());
        citizen.setGender(dto.getGender());
        citizen.setSpouseName(dto.getSpouseName());
        citizen.setFatherName(dto.getFatherName());
        citizen.setMotherName(dto.getMotherName());
        citizen.setGrandfatherName(dto.getGrandfatherName());
        citizen.setGrandmotherName(dto.getGrandmotherName());
        citizen.setNationality(dto.getNationality());
        citizen.setDistrict(dto.getDistrict());
        citizen.setWardNo(dto.getWardNo());
        citizen.setMunicipality(dto.getMunicipality());
        citizen.setTole(dto.getTole());
        citizen.setPhoneNo(dto.getPhoneNo());
        citizen.setVerifiedBy(dto.getVerifiedBy());
        citizen.setVerifiedDate(dto.getVerifiedDate());
        citizen.setUpdatedAt(dto.getUpdatedAt());
        citizen.setVerified(dto.isVerified());
    }



}

