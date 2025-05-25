package com.kalyan.smartmunicipality.whatsapp.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "whatsapp")
public class WhatsappProperties {
    private String accessToken;
    private String phoneNumberId;

}
