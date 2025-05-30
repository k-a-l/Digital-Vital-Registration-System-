package com.kalyan.smartmunicipality.user.dto;

import com.kalyan.smartmunicipality.staff.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDto {
    private String email;
    private String password;
    private Role role;
    private LocalDate createdAt;
}
