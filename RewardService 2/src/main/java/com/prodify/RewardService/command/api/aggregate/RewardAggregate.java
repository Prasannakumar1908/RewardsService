package com.prodify.RewardService.command.api.aggregate;

import com.prodify.RewardService.command.api.command.CreateRewardCommand;
import com.prodify.RewardService.command.api.events.RewardCreatedEvent;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;

@Aggregate
@Getter
@Setter
public class RewardAggregate {
    @AggregateIdentifier
    private String rewardId;
    private String rewardName;
    private Integer points;

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public RewardAggregate() {

    }
    @CommandHandler
    public RewardAggregate(CreateRewardCommand createRewardCommand) {
        RewardCreatedEvent rewardCreatedEvent = new RewardCreatedEvent(
                createRewardCommand.getRewardId(),
                createRewardCommand.getRewardName(),
                createRewardCommand.getPoints());
        BeanUtils.copyProperties(createRewardCommand, rewardCreatedEvent);
        AggregateLifecycle.apply(rewardCreatedEvent);
    }

    @EventSourcingHandler
    public void on(RewardCreatedEvent event) {
        this.rewardId = event.getRewardId();
        this.rewardName = event.getRewardName();
        this.points = event.getPoints();
    }
}
