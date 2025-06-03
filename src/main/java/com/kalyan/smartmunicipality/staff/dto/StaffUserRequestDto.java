package com.kalyan.smartmunicipality.staff.dto;

import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffUserRequestDto {

        private String fullName;
        private String email;
        private String password;
        private String phoneNumber;
        private String designation;
        private String department;
        private StaffUser addedBy;
        private Role role;
    }


