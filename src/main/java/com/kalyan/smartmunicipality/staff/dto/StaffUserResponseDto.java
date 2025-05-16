package com.kalyan.smartmunicipality.staff.dto;

import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.staff.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffUserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String designation;
    private String department;
    private Status status;
    private LocalDateTime createdAt;
    private String addedByName;

}
