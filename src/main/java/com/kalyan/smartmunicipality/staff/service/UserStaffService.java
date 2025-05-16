package com.kalyan.smartmunicipality.staff.service;

import com.kalyan.smartmunicipality.staff.dto.StaffUserRequestDto;
import com.kalyan.smartmunicipality.staff.dto.StaffUserResponseDto;
import com.kalyan.smartmunicipality.staff.mapper.StaffUserDtoMapper;
import com.kalyan.smartmunicipality.staff.model.StaffUser;
import com.kalyan.smartmunicipality.staff.repository.StaffUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStaffService {
    private final StaffUserRepository staffUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StaffUserResponseDto saveStaffUser(StaffUserRequestDto staffUserRequestDto) {
        if(staffUserRequestDto.getAddedBy() == null) {
            throw new RuntimeException("Super Admin is Required");

        }
    StaffUser staffUser=StaffUserDtoMapper.toEntityDto(staffUserRequestDto,staffUserRequestDto.getAddedBy(),passwordEncoder);
        staffUserRepository.save(staffUser);
        return StaffUserDtoMapper.staffUserResponseDto(staffUser);
    }
}
