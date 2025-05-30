package com.kalyan.smartmunicipality.user.dto;

import com.kalyan.smartmunicipality.staff.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String jwtToken;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
}
