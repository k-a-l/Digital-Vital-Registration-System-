package com.kalyan.smartmunicipality.citizen.mapper;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.model.Citizen;

import java.time.LocalDate;

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
                .municipality(citizen.getMunicipality())
                .wardNo(citizen.getWardNo())
                .gender(citizen.getGender())
                .tole(citizen.getTole())
                .spouseName(citizen.getSpouseName())
                .verifiedBy(citizen.getVerifiedBy())
                .verifiedDate(citizen.getVerifiedDate())
                .createdAt(citizen.getCreatedAt())
                .reasonForRejection(citizen.getReasonForRejection())
                .status(citizen.getStatus())
                .marriageStatus(citizen.getMarriageStatus())
                .citizenshipNumber(citizen.getCitizenshipNumber())
                .updatedAt(citizen.getUpdatedAt())


                .build();

    }
    //save - create
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
                .createdAt(LocalDate.now())
               // .updatedAt(dto.getUpdatedAt())
                .verifiedBy(dto.getVerifiedBy())
                .verifiedDate(dto.getVerifiedDate())
                .status(CitizenStatus.PENDING)
                .marriageStatus(dto.getMarriageStatus())
                .citizenshipNumber(dto.getCitizenshipNumber())
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
        citizen.setMarriageStatus(dto.getMarriageStatus());
        citizen.setCitizenshipNumber(dto.getCitizenshipNumber());
        citizen.setStatus(CitizenStatus.PENDING);
        citizen.setUpdatedAt(LocalDate.now());
    }



}

