package com.prodify.RewardService.command.api.saga;

import com.prodify.RewardService.command.api.events.RewardCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import java.util.logging.Logger;

@Saga
public class RewardProcessingSaga {

    Logger log = Logger.getLogger(RewardProcessingSaga.class.getName());
    @StartSaga
    @SagaEventHandler(associationProperty = "rewardId")
    private void handle(RewardCreatedEvent event) {
        log.info("Handling reward created event: {} in saga for rewardId:{}");
    }

}
