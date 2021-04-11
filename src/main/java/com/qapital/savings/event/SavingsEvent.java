package com.qapital.savings.event;

import com.qapital.bankdata.transaction.Transaction;
import com.qapital.savings.rule.SavingsRule;
import com.qapital.savings.rule.SavingsRule.RuleType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;

/**
 * A Savings Event represents an event in the history of a Savings Goal.
 * Events can be either monetary (triggered by the application of Savings Rules,
 * manual transfers, interest payments or incentive payouts), or other events
 * of significance in the history of the goal, such as pausing or unpausing
 * Savings Rules or other users joining or leaving a shared goal.
 */

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SavingsEvent {


    private Long id;

    private Long userId;
    private Long savingsGoalId;
    private Long savingsRuleId;
    private EventName eventName;
    private LocalDate date;
    private Double amount;
    private Long triggerId;
    private RuleType ruleType;
    private Long savingsTransferId;
    private Boolean cancelled;
    private Instant created;

    private Transaction transaction = null;

    public enum EventName {
        manual, started, stopped, rule_application, ifttt_transfer, joined, withdrawal, internal_transfer, cancellation, incentive_payout, interest
    }


    public SavingsEvent(Transaction transaction, Long savingsGoalId, Long savingsRuleId, EventName eventName, Long triggerId, SavingsRule savingsRule) {
        log.info("new savings event created for goalId: " + savingsGoalId);

        try {
            this.transaction = transaction;

        } catch (Exception e) {
            log.error("failed to initialise a transaction due to exception of type: " + e.getMessage());
            transaction = Transaction.builder()
                    .id(1L)
                    .userId(1L)
                    .amount(0d)
                    .description("-")
                    .date(LocalDate.of(2021, 1, 1))
                    .build();
        }

        this.userId = transaction.getUserId();
        this.savingsGoalId = savingsGoalId;
        this.savingsRuleId = savingsRuleId;
        this.eventName = eventName;
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.triggerId = triggerId;
        this.ruleType = savingsRule.getRuleType();
        this.created = Instant.now();
    }

}
