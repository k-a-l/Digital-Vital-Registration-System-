package com.kalyan.smartmunicipality.staff.controller;

import com.kalyan.smartmunicipality.staff.dto.StaffUserRequestDto;
import com.kalyan.smartmunicipality.staff.dto.StaffUserResponseDto;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import com.kalyan.smartmunicipality.staff.service.UserStaffService;
import com.kalyan.smartmunicipality.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffUserController {

    private final UserStaffService userStaffService;

    @PostMapping("/super-admin")
    public ResponseEntity<StaffUserResponseDto> createSuperAdmin(@RequestBody StaffUserRequestDto dto) {
        StaffUserResponseDto response = userStaffService.createSuperAdmin(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<StaffUserResponseDto> createStaff(@RequestBody StaffUserRequestDto dto, @AuthenticationPrincipal StaffUser staffUser) {
        StaffUserResponseDto response = userStaffService.createStaff(dto, staffUser);
        return ResponseEntity.ok(response);

    }
}
