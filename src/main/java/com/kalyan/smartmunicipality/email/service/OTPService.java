package com.kalyan.smartmunicipality.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class OTPService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public OTPService(JavaMailSender mailSender, StringRedisTemplate redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    public void generateAndSendOTP(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP

        // Store in Redis for 5 minutes
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email, otp, 5, TimeUnit.MINUTES);

        // Send OTP via email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + ". It is valid for 5 minutes.");
        mailSender.send(message);
    }

    public boolean verifyOTP(String email, String inputOtp) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedOtp = ops.get(email);
        return cachedOtp != null && cachedOtp.equals(inputOtp);
    }
}
