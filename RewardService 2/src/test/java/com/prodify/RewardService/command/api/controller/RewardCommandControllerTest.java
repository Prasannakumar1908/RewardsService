package com.prodify.RewardService.command.api.controller;

import com.prodify.RewardService.command.api.command.CreateRewardCommand;
import com.prodify.RewardService.command.api.kafka.RewardKafkaProducer;
import com.prodify.RewardService.command.api.model.RewardRestModel;
import com.prodify.RewardService.command.api.util.RequestIdContext;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RewardCommandControllerTest {

    private CommandGateway commandGateway;
    private RewardKafkaProducer rewardKafkaProducer;
    private RequestIdContext requestIdContext;
    private RewardCommandController controller;

    @BeforeEach
    void setUp() {
        commandGateway = mock(CommandGateway.class);
        rewardKafkaProducer = mock(RewardKafkaProducer.class);
        requestIdContext = mock(RequestIdContext.class);
        controller = new RewardCommandController(commandGateway, rewardKafkaProducer, requestIdContext);
    }

    @Test
    void createReward_success() {
        RewardRestModel rewardRestModel = new RewardRestModel("TestReward", 100);
        when(requestIdContext.getRequestId()).thenReturn("test-request-id");
        doNothing().when(rewardKafkaProducer).sendRewardEvent(anyString(), anyString(), any());

        ResponseEntity<String> response = controller.createReward(rewardRestModel);

        verify(commandGateway, times(1)).sendAndWait(any(CreateRewardCommand.class));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void createReward_commandExecutionException() {
        RewardRestModel rewardRestModel = new RewardRestModel("TestReward", 100);
        when(requestIdContext.getRequestId()).thenReturn("test-request-id");
        doThrow(new RuntimeException()).when(commandGateway).sendAndWait(any(CreateRewardCommand.class));

        ResponseEntity<String> response = controller.createReward(rewardRestModel);

        assertEquals(500, response.getStatusCodeValue());
    }
}
