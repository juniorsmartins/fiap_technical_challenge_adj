package br.com.fiap.tech.challenge_user.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateKafkaConfig {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Bean
    public NewTopic usuarioCreateTopic() {

        return TopicBuilder
                .name(topic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}

