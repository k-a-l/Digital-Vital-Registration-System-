package com.kalyan.smartmunicipality.email.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    private final Logger log = LoggerFactory.getLogger(OTPService.class);
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    private static final long OTP_EXPIRATION_MINUTES = 5;
    private static final long OTP_COOLDOWN_SECONDS = 60;

    @Autowired
    public OTPService(JavaMailSender mailSender, StringRedisTemplate redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    public void generateAndSendOTP(String email) {
        String normalizedEmail = normalizeEmail(email);
        String redisKey = "otp:" + normalizedEmail;

        // Cooldown check: prevent frequent requests
        Boolean exists = redisTemplate.hasKey(redisKey);
        if (exists != null && exists) {
            throw new RuntimeException("OTP already sent. Please wait before requesting again." + OTP_COOLDOWN_SECONDS);
        }

        // Generate 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // Store in Redis with expiration
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(redisKey, otp, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(normalizedEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + ". It is valid for " + OTP_EXPIRATION_MINUTES + " minutes.");
        mailSender.send(message);

        log.info("OTP sent to {}: {}", normalizedEmail, otp);
    }

    public boolean verifyOTP(String email, String inputOtp) {
        String normalizedEmail = normalizeEmail(email);
        String redisKey = "otp:" + normalizedEmail;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedOtp = ops.get(redisKey);

        if (cachedOtp != null && cachedOtp.equals(inputOtp)) {
            redisTemplate.delete(redisKey); // Delete after successful verification
            return true;
        }
        return false;
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}
