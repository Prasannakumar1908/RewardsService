package com.prodify.RewardService.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@Builder
public class CreateRewardCommand {

    @TargetAggregateIdentifier
    private String rewardId;
    private String rewardName;
    private int points;

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

    public int getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public CreateRewardCommand(String rewardId, String rewardName, Integer points) {
        this.rewardId = rewardId;
        this.rewardName = rewardName;
        this.points = points;
    }

    public CreateRewardCommand() {
    }
}
