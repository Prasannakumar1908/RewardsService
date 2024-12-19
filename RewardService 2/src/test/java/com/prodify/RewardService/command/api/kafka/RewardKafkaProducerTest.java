package com.prodify.RewardService.command.api.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;


import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RewardKafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private RewardKafkaProducer rewardKafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rewardKafkaProducer = new RewardKafkaProducer(kafkaTemplate, objectMapper);
    }

    @Test
    void testSendRewardEvent_Success() throws JsonProcessingException {
        // Arrange
        String topic = "test-topic";
        String rewardId = "reward123";
        Object rewardRestModel = new Object(); // Mock object
        String serializedRewardData = "{\"rewardName\": \"Test Reward\", \"points\": 100}";
        String expectedMessage = "{\"rewardId\": \"reward123\", \"rewardData\": " + serializedRewardData + "}";

        when(objectMapper.writeValueAsString(rewardRestModel)).thenReturn(serializedRewardData);

        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(eq(topic), eq(expectedMessage))).thenReturn(future);

        // Act
        rewardKafkaProducer.sendRewardEvent(topic, rewardId, rewardRestModel);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq(topic), captor.capture());
        assertEquals(expectedMessage, captor.getValue());
    }

    @Test
    void testSendRewardEvent_Failure() throws JsonProcessingException {
        // Arrange
        String topic = "test-topic";
        String rewardId = "reward123";
        Object rewardRestModel = new Object(); // Mock object
        String serializedRewardData = "{\"rewardName\": \"Test Reward\", \"points\": 100}";
        String expectedMessage = "{\"rewardId\": \"reward123\", \"rewardData\": " + serializedRewardData + "}";

        when(objectMapper.writeValueAsString(rewardRestModel)).thenReturn(serializedRewardData);

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka error"));
        when(kafkaTemplate.send(eq(topic), eq(expectedMessage))).thenReturn(future);

        // Act
        rewardKafkaProducer.sendRewardEvent(topic, rewardId, rewardRestModel);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq(topic), eq(expectedMessage));
    }

    @Test
    void testSendRewardEvent_JsonProcessingException() throws JsonProcessingException {
        // Arrange
        String topic = "test-topic";
        String rewardId = "reward123";
        Object rewardRestModel = new Object();

        when(objectMapper.writeValueAsString(rewardRestModel)).thenThrow(new JsonProcessingException("Serialization error") {
        });

        // Act
        rewardKafkaProducer.sendRewardEvent(topic, rewardId, rewardRestModel);

        // Assert
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }
}
