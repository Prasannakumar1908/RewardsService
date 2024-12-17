package com.prodify.RewardService.command.api.events;

import com.prodify.RewardService.command.api.data.Reward;
import com.prodify.RewardService.command.api.data.RewardRepository;
import com.prodify.RewardService.command.api.events.RewardCreatedEvent;
import com.prodify.RewardService.command.api.events.RewardEventsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RewardEventsHandlerTest {

    private RewardRepository rewardRepository;
    private RewardEventsHandler handler;

    @BeforeEach
    void setUp() {
        rewardRepository = mock(RewardRepository.class);
        handler = new RewardEventsHandler(rewardRepository);
    }

    @Test
    void testRewardCreatedEvent() {
        RewardCreatedEvent event = new RewardCreatedEvent("reward123", "RewardName", 100);

        handler.on(event);

        verify(rewardRepository, times(1)).save(any(Reward.class));
    }
}
