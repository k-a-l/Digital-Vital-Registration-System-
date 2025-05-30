package com.kalyan.smartmunicipality.user.service;

import com.kalyan.smartmunicipality.jwt.utils.JwtUtil;
import com.kalyan.smartmunicipality.user.dto.UserRequestDto;
import com.kalyan.smartmunicipality.user.dto.UserResponseDto;
import com.kalyan.smartmunicipality.user.mapper.UserMapper;
import com.kalyan.smartmunicipality.user.model.User;
import com.kalyan.smartmunicipality.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
    if(userRepository.existsByEmail(userRequestDto.getEmail())){

        throw new RuntimeException("Email already in use");
    }
        User user = userMapper.toEntity(userRequestDto);

        user.setJwtToken(jwtUtil.generateToken(user.getEmail()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserResponseDto loginUser(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));

        if(!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        user.setJwtToken(token);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
