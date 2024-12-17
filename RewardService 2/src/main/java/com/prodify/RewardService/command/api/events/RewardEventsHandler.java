package com.prodify.RewardService.command.api.events;

import com.prodify.RewardService.command.api.data.Reward;
import com.prodify.RewardService.command.api.data.RewardRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RewardEventsHandler {

    private RewardRepository rewardRepository;

    public RewardEventsHandler(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @EventHandler
    public void on(RewardCreatedEvent event) {
        Reward reward = new Reward();
        BeanUtils.copyProperties(event, reward);
        rewardRepository.save(reward);
    }
}
