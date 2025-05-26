package com.kalyan.smartmunicipality.whatsapp.controller;

import com.kalyan.smartmunicipality.whatsapp.service.WhatsappService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@RequestMapping("/api/v1/whatsapp/otp")
@RestController
public class OtpController {

    private final WhatsappService whatsappService;

    // Validate Nepal phone number
    public boolean isValidNepalPhoneNumber(String number) {
        return number != null && number.matches("^\\+9779[78]\\d{8}$");
    }



    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        if (!isValidNepalPhoneNumber(phone)) {
            return ResponseEntity.badRequest().body("Invalid Nepal phone number format.");
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(900000) + 100000);

        // TODO: Save OTP in DB/cache with expiry for verification later

        whatsappService.sendOtp(phone, otp);

        return ResponseEntity.ok("OTP sent successfully to WhatsApp.");
    }

}
