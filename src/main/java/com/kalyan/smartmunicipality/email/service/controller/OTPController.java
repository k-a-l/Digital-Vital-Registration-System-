package com.kalyan.smartmunicipality.email.service.controller;

import com.kalyan.smartmunicipality.email.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email/otp")
public class OTPController {
    private final OTPService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOTP(@RequestParam String email){
        otpService.generateAndSendOTP(email);
        return ResponseEntity.ok("OTP sent to " + email);

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String otp){
        if(otpService.verifyOTP(email, otp)){
            return ResponseEntity.ok("OTP verified successfully.");
        }else{
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }

}
