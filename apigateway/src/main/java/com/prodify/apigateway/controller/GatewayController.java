package com.prodify.apigateway.controller;

import com.prodify.apigateway.Model.*;
import com.prodify.apigateway.util.GatewayServiceDecorator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gateway")
@Slf4j
public class GatewayController {

    private final GatewayServiceDecorator gatewayServiceDecorator;

    public GatewayController(GatewayServiceDecorator gatewayServiceDecorator) {
        this.gatewayServiceDecorator = gatewayServiceDecorator;
    }

    @Tag(name = "Reward Service", description = "Operations for managing rewards")
    @Operation(summary = "Create a reward", description = "Create a reward with a payload")
    @PostMapping("/reward")
    public Mono<ResponseEntity<String>> createReward(@RequestBody RewardRestModel rewardRestModel) {
        log.info("Creating Reward with details: {}", rewardRestModel);
        return gatewayServiceDecorator.execute("REWARD_SERVICE_URL", "reward", HttpMethod.POST, rewardRestModel, new ParameterizedTypeReference<String>() {
        });
    }

}
