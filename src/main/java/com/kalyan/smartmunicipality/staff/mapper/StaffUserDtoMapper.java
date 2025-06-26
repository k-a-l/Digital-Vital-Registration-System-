package com.kalyan.smartmunicipality.staff.mapper;

import com.kalyan.smartmunicipality.jwt.utils.JwtUtil;
import com.kalyan.smartmunicipality.staff.enums.Status;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import com.kalyan.smartmunicipality.staff.dto.StaffUserRequestDto;
import com.kalyan.smartmunicipality.staff.dto.StaffUserResponseDto;
import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StaffUserDtoMapper {
    public static StaffUserResponseDto staffUserResponseDto(StaffUser staffUser){
        return StaffUserResponseDto.builder()
                .id(staffUser.getId())
                .fullName(staffUser.getFullName())
                .email(staffUser.getEmail())
                .role(staffUser.getRole())
                .status(staffUser.getStatus())
                .department(staffUser.getDepartment())
                .designation(staffUser.getDesignation())
                .phoneNumber(staffUser.getPhoneNumber())
                .createdAt(staffUser.getCreatedAt())
                .addedBy(staffUser.getAddedBy())


                .build();

    }

    public static StaffUser toEntityDto(StaffUserRequestDto staffUserRequestDto, StaffUser addedBy, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        StaffUser savedStaff = StaffUser.builder()
                .fullName(staffUserRequestDto.getFullName())
                .email(staffUserRequestDto.getEmail())
                .password(passwordEncoder.encode(staffUserRequestDto.getPassword()))
                .department(staffUserRequestDto.getDepartment())
                .designation(staffUserRequestDto.getDesignation())
                .phoneNumber(staffUserRequestDto.getPhoneNumber())
                .role(staffUserRequestDto.getRole() != null ? staffUserRequestDto.getRole() : Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .addedBy(staffUserRequestDto.getAddedBy())

                .build();


        User user = User.builder()
                .email(savedStaff.getEmail())
                .password(savedStaff.getPassword())
                .role(savedStaff.getRole())
                .staffUser(savedStaff)
                .createdAt(LocalDateTime.now())
                .jwtToken(jwtUtil.generateToken(savedStaff.getEmail()))

                .build();
        savedStaff.setUser(user);
        return savedStaff;



    }


}
