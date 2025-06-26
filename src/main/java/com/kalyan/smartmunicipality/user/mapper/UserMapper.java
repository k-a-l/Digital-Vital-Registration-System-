package com.kalyan.smartmunicipality.user.mapper;

import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import com.kalyan.smartmunicipality.user.dto.UserRequestDto;
import com.kalyan.smartmunicipality.user.dto.UserResponseDto;
import com.kalyan.smartmunicipality.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    // Converts incoming request DTO to User entity for saving
    public User toEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole() != null ? userRequestDto.getRole() : Role.CITIZEN) // set from DTO or default
                .createdAt(LocalDateTime.now())
                .build();
    }

    // Converts saved User entity to response DTO for client
    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .jwtToken(user.getJwtToken())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .department(user.getStaffUser() !=null? user.getStaffUser().getDepartment().name() : null)
                .build();
    }
}
