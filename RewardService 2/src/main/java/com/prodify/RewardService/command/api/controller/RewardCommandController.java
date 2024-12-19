package com.prodify.RewardService.command.api.controller;

import com.prodify.RewardService.command.api.command.CreateRewardCommand;
import com.prodify.RewardService.command.api.kafka.RewardKafkaProducer;
import com.prodify.RewardService.command.api.model.RewardRestModel;
import com.prodify.RewardService.command.api.util.RequestIdContext;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/rewards")
@Slf4j
public class RewardCommandController {

    private final CommandGateway commandGateway;
    private final RewardKafkaProducer rewardKafkaProducer;
    private final RequestIdContext requestIdContext;

    @Autowired
    public RewardCommandController(CommandGateway commandGateway, RewardKafkaProducer rewardKafkaProducer, RequestIdContext requestIdContext) {
        this.commandGateway = commandGateway;
        this.rewardKafkaProducer = rewardKafkaProducer;
        this.requestIdContext = requestIdContext;
    }

    @PostMapping("/reward")
    public ResponseEntity<String> createReward(@RequestBody RewardRestModel rewardRestModel) {
        String requestId = requestIdContext.getRequestId();
        String rewardId = UUID.randomUUID().toString();
        log.info("Recevied create reward with requestId:{},rewardName:{}", requestId, rewardRestModel.getRewardName());
        CreateRewardCommand createRewardCommand =
                new CreateRewardCommand(rewardId, rewardRestModel.getRewardName(), rewardRestModel.getPoints());
        try {
            log.debug("Sending CreateRewardCommand with requestId:{},rewardId:{}", requestId, rewardId);
            commandGateway.sendAndWait(createRewardCommand);
            rewardKafkaProducer.sendRewardEvent("reward-events", rewardId, rewardRestModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reward created with ID:" + rewardId);
        } catch (CommandExecutionException e) {
            log.error("Failed to execute CreateRewardCommand", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error creating reward");
        } catch (Exception e) {
            log.error("Unexpected error while creating CreateRewardCommand", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating reward");
        }
    }

}
