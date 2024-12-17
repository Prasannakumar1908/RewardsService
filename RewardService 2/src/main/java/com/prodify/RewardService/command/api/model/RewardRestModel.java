package com.prodify.RewardService.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class RewardRestModel {
    private String rewardName;
    private int points;
    public RewardRestModel(String rewardName, int points) {
        this.rewardName = rewardName;
        this.points = points;
    }

    public RewardRestModel() {
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

    public void setPoints(int points) {
        this.points = points;
    }
}
