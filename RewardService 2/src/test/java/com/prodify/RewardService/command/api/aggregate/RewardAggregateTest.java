package com.prodify.RewardService.command.api.aggregate;

import com.prodify.RewardService.command.api.command.CreateRewardCommand;
import com.prodify.RewardService.command.api.events.RewardCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardAggregateTest {

    private AggregateTestFixture<RewardAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(RewardAggregate.class);
    }

    @Test
    void testCreateRewardCommand() {
        String rewardId = "123";
        String rewardName = "Test Reward";
        int points = 100;

        CreateRewardCommand command = new CreateRewardCommand(rewardId, rewardName, points);

        fixture.givenNoPriorActivity()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new RewardCreatedEvent(rewardId, rewardName, points))
                .expectState(aggregate -> {
                    assertEquals(rewardId, aggregate.getRewardId());
                    assertEquals(rewardName, aggregate.getRewardName());
                    assertEquals(points, aggregate.getPoints());
                });
    }

}
