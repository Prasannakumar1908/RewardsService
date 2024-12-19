package com.prodify.RewardService.command.api.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class RewardKafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(RewardKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RewardKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendRewardEvent(String topic, String rewardId, Object rewardRestModel) {
        try {
            String rewardData = objectMapper.writeValueAsString(rewardRestModel);
            String message = "{\"rewardId\": \"" + rewardId + "\", \"rewardData\": " + rewardData + "}"; // Including orderId
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Reward event successfully to kafka. topic: " + topic + ", rewardId: " + rewardId + "topic:{},message:{}", topic, message);
                } else {
                    log.error("Failed to send reward event to kafka. topic:{},reward Data:{}", topic, rewardData, ex);
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize rewardRestModel to kafka", e);
        }

    }
}
