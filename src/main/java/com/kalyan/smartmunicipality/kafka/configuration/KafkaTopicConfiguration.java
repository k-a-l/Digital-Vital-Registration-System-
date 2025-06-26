package com.kalyan.smartmunicipality.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder
                .name("notification-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
