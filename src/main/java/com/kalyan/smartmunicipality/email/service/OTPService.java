package com.kalyan.smartmunicipality.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class OTPService {
    private final JavaMailSender javaMailSender;
    private final StringRedisTemplate stringRedisTemplate;

    public OTPService(JavaMailSender javaMailSender, StringRedisTemplate stringRedisTemplate) {
        this.javaMailSender = javaMailSender;
        this.stringRedisTemplate = stringRedisTemplate;
    }


}
