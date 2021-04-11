package com.qapital.savings.rule;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * The core configuration object for a Savings Rule.
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Data
public class SavingsRule {

    private Long id;
    private Long userId;

    private String placeDescription;
    private Double amount;
    private List<Long> savingsGoalIds;
    private RuleType ruleType;
    private RuleStatus ruleStatus;


    public enum RuleType {
        GUILTYPLEASURE, ROUNDUP
    }


    public enum RuleStatus {
        ACTIVE, DELETED, PAUSED
    }




    public static SavingsRule createGuiltyPleasureRule(Long id, Long userId, String placeDescription, Double penaltyAmount) {
        log.info("creating GuiltyPleasure with id: " + id);

        SavingsRule guiltyPleasureRule = new SavingsRule();
        guiltyPleasureRule.setId(id);
        guiltyPleasureRule.setUserId(userId);
        guiltyPleasureRule.setPlaceDescription(placeDescription);
        guiltyPleasureRule.setAmount(penaltyAmount);
        guiltyPleasureRule.setSavingsGoalIds(new ArrayList<>());
        guiltyPleasureRule.setRuleType(RuleType.GUILTYPLEASURE);
        guiltyPleasureRule.setStatus(RuleStatus.ACTIVE);
        return guiltyPleasureRule;
    }

    public static SavingsRule createRoundupRule(Long id, Long userId, Double roundupToNearest) {
        log.info("creating RoundupRule with id: " + id);

        SavingsRule roundupRule = new SavingsRule();
        roundupRule.setId(id);
        roundupRule.setUserId(userId);
        roundupRule.setAmount(roundupToNearest);
        roundupRule.setSavingsGoalIds(new ArrayList<>());
        roundupRule.setRuleType(RuleType.ROUNDUP);
        roundupRule.setStatus(RuleStatus.ACTIVE);
        return roundupRule;
    }

    public void addSavingsGoal(Long savingsGoalId) {
        log.info("adding savingsGoal with id: " + savingsGoalId);
        if (!savingsGoalIds.contains(savingsGoalId)) {
            savingsGoalIds.add(savingsGoalId);
        }
    }

    public void removeSavingsGoal(Long savingsGoalId) {
        log.info("removing savingsGoal with id: " + savingsGoalId);
        savingsGoalIds.remove(savingsGoalId);
    }

    public RuleStatus getStatus() {
        return ruleStatus;
    }

    public void setStatus(RuleStatus ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public boolean isActive() {
        return RuleStatus.ACTIVE.equals(getStatus());
    }


}
