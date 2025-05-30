package com.kalyan.smartmunicipality.user.controller;

import com.kalyan.smartmunicipality.user.dto.UserRequestDto;
import com.kalyan.smartmunicipality.user.dto.UserResponseDto;
import com.kalyan.smartmunicipality.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.registerUser(requestDto);
        return ResponseEntity.ok(responseDto);

    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> existEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));

    }




}
