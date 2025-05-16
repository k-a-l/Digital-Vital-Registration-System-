package com.kalyan.smartmunicipality.staff.mapper;

import com.kalyan.smartmunicipality.staff.model.StaffUser;
import com.kalyan.smartmunicipality.staff.dto.StaffUserRequestDto;
import com.kalyan.smartmunicipality.staff.dto.StaffUserResponseDto;
import com.kalyan.smartmunicipality.staff.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component

public class StaffUserDtoMapper {
    public static StaffUserResponseDto staffUserResponseDto(StaffUser staffUser){
        return StaffUserResponseDto.builder()
                .fullName(staffUser.getFullName())
                .email(staffUser.getEmail())
                .role(staffUser.getRole())
                .status(staffUser.getStatus())
                .department(staffUser.getDepartment())
                .designation(staffUser.getDesignation())
                .phoneNumber(staffUser.getPhoneNumber())
                .createdAt(staffUser.getCreatedAt())
                .addedByName(staffUser.getAddedBy().getFullName())
                .build();

    }

    public static StaffUser toEntityDto(StaffUserRequestDto staffUserRequestDto, StaffUser addedBy, BCryptPasswordEncoder passwordEncoder){
        return StaffUser.builder()
                .fullName(staffUserRequestDto.getFullName())
                .email(staffUserRequestDto.getEmail())
                .password(passwordEncoder.encode(staffUserRequestDto.getPassword()))
                .department(staffUserRequestDto.getDepartment())
                .designation(staffUserRequestDto.getDesignation())
                .phoneNumber(staffUserRequestDto.getPhoneNumber())
                .role(Role.STAFF)
                .createdAt(LocalDateTime.now())
                .addedBy(addedBy)

                .build();

    }


}
