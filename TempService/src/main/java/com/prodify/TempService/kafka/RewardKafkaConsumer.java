package com.prodify.TempService.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodify.TempService.events.RewardEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RewardKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(RewardKafkaConsumer.class);
    private final ObjectMapper objectMapper;

    public RewardKafkaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics="reward-events",groupId="temp-service-group")
    public void listenrewardEvents(String rewardData){
        try{
            log.info("Received raw reward event: " + rewardData);
            JsonNode jsonNode = objectMapper.readTree(rewardData);
            String rewardId = jsonNode.get("rewardId").asText();  // Extract orderId from the message
            JsonNode rewardDataNode = jsonNode.get("rewardData"); // Extract the orderData field
            if(rewardDataNode!=null){
                RewardEvent rewardEvent = objectMapper.treeToValue(rewardDataNode, RewardEvent.class);
                processRewardEvent(rewardEvent);
            }
            else{
                log.warn("Received Kafka message with no rewardData field: " + rewardData);
            }
        }
        catch (JsonProcessingException e){
            log.error("Failed to deserialze reward event: " + rewardData, e);
        }
    }

    private void processRewardEvent(RewardEvent rewardEvent) {

        log.info("Processing reward for rewardId:{} with points:{]", rewardEvent.getRewardId(), rewardEvent.getPoints());
    }
}
