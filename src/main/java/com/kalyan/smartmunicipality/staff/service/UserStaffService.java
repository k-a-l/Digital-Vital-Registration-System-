package com.kalyan.smartmunicipality.staff.service;

import com.kalyan.smartmunicipality.jwt.utils.JwtUtil;
import com.kalyan.smartmunicipality.staff.dto.StaffUserRequestDto;
import com.kalyan.smartmunicipality.staff.dto.StaffUserResponseDto;
import com.kalyan.smartmunicipality.staff.enums.Role;
import com.kalyan.smartmunicipality.staff.enums.Status;
import com.kalyan.smartmunicipality.staff.mapper.StaffUserDtoMapper;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import com.kalyan.smartmunicipality.staff.repository.StaffUserRepository;
import com.kalyan.smartmunicipality.user.model.User;
import com.kalyan.smartmunicipality.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserStaffService {

    private final StaffUserRepository staffUserRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public StaffUserResponseDto createSuperAdmin(StaffUserRequestDto staffUserRequestDto) {
        if (staffUserRepository.findByEmail(staffUserRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists.");
        }

        // Create StaffUser
        StaffUser superAdmin = StaffUser.builder()
                .fullName(staffUserRequestDto.getFullName())
                .email(staffUserRequestDto.getEmail())
                .role(Role.SUPERADMIN)
                .department(staffUserRequestDto.getDepartment())
                .designation(staffUserRequestDto.getDesignation())
                .phoneNumber(staffUserRequestDto.getPhoneNumber())
                .password(passwordEncoder.encode(staffUserRequestDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();

        StaffUser savedStaff = staffUserRepository.save(superAdmin);

        // Create corresponding User
        String token = jwtUtil.generateToken(savedStaff.getEmail());

        User user = User.builder()
                .email(savedStaff.getEmail())
                .password(savedStaff.getPassword())
                .role(savedStaff.getRole())
                .staffUser(savedStaff)
                .createdAt(LocalDateTime.now())
                .jwtToken(token)
                .build();

        userRepository.save(user);

        return StaffUserDtoMapper.staffUserResponseDto(savedStaff);
    }

    public StaffUserResponseDto createStaff(StaffUserRequestDto staffUserRequestDto, StaffUser addedBy) {
        if(staffUserRepository.existsByEmail(staffUserRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        StaffUser staffUser = StaffUserDtoMapper.toEntityDto(staffUserRequestDto,addedBy,passwordEncoder,jwtUtil);
        StaffUser savedStaff = staffUserRepository.save(staffUser);


        return StaffUserDtoMapper.staffUserResponseDto(staffUser);

    }
}
