package com.kalyan.smartmunicipality;

import com.kalyan.smartmunicipality.whatsapp.properties.WhatsappProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(WhatsappProperties.class)

public class SmartMunicipalityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartMunicipalityApplication.class, args);
    }

}
