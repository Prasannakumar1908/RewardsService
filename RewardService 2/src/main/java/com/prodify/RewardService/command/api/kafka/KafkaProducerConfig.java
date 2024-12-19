package com.prodify.RewardService.command.api.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 0);  // Retry up to 3 times
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");  // Ensure all replicas acknowledge
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);  // Timeout after 5 seconds

        try {
            logger.debug("Attempting to connect to Kafka");
            logger.info("Initializing Kafka Producer Factory with configuration: {}", configProps);
            return new DefaultKafkaProducerFactory<>(configProps);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid kafka producer configuration:{}", e.getMessage());
            logger.error("Invalid Kafka producer configuration: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.warn("Unexpected error initializing Kafka Producer Factory{}", e.getMessage());
            logger.error("Unexpected error initializing Kafka Producer Factory: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize Kafka Producer Factory", e);
        }
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        try {
            logger.debug("Attempting to create kafka template");
            logger.info("Creating Kafka Template");
            return new KafkaTemplate<>(producerFactory());
        } catch (TimeoutException e) {
            logger.warn("Timeout while creating Kafka Template:{}", e.getMessage());
            logger.error("Timeout while creating Kafka Template: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.warn("Unexpected error creating Kafka Template:{}", e.getMessage());
            logger.error("Unexpected error creating Kafka Template: {}", e.getMessage());
            throw new RuntimeException("Failed to create Kafka Template", e);
        }
    }
}
