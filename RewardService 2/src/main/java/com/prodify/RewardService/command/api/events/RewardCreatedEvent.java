package com.prodify.RewardService.command.api.events;

import lombok.*;

@Data
@Builder
public class RewardCreatedEvent {
    private String rewardId;
    private String rewardName;
    private Integer points;

    public RewardCreatedEvent(String rewardId, String rewardName, Integer points) {
        this.rewardId = rewardId;
        this.rewardName = rewardName;
        this.points = points;
    }

    public RewardCreatedEvent() {
    }

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
}
