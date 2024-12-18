package com.prodify.RewardService.command.api.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="rewards")
public class Reward {

    @Id
    private String rewardId;
    private String rewardName;
    private int points;
}
