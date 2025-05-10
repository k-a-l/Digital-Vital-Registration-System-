package com.kalyan.smartmunicipality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.kalyan.smartmunicipality.citizen.model")
@EnableJpaRepositories(basePackages = "com.kalyan.smartmunicipality.citizen.repository")

public class SmartMunicipalityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartMunicipalityApplication.class, args);
    }

}
