package com.kalyan.smartmunicipality.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth-> auth.anyRequest().permitAll())
                //.httpBasic(httpBasic->httpBasic.disable())
                .httpBasic(AbstractHttpConfigurer::disable)
                //.csrf(csrf-> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
