package com.qapital.savings.rule;

import com.qapital.bankdata.transaction.Transaction;
import com.qapital.bankdata.transaction.TransactionsService;
import com.qapital.savings.event.SavingsEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j

@Service
public class StandardSavingsRulesService implements SavingsRulesService {

    private static double presentSavings = 0;

    private final TransactionsService transactionsService;

    @Autowired
    public StandardSavingsRulesService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }


    public static double getPresentSavings() {
        return presentSavings;
    }

    @Override
    public List<SavingsRule> activeRulesForUser(Long userId) {

        SavingsRule guiltyPleasureRule = SavingsRule.createGuiltyPleasureRule(1l, userId, "Starbucks", 3.00d);
        guiltyPleasureRule.addSavingsGoal(1l);
        guiltyPleasureRule.addSavingsGoal(2l);
        SavingsRule roundupRule = SavingsRule.createRoundupRule(2l, userId, 2.00d);
        roundupRule.addSavingsGoal(1l);

        return List.of(guiltyPleasureRule, roundupRule);
    }

    @Override
    public List<SavingsEvent> executeRule(SavingsRule savingsRule) {
        log.info("A rule execution is happening for rule id: " + savingsRule.getId());

        Validate.notNull(savingsRule);
        List<SavingsEvent> listOfSavingsEvents = new ArrayList<>();
        List<Transaction> transactions;

        SavingsEvent newSavings;

        transactions = transactionsService.latestTransactionsForUser(1L);

        long count = 0L;

        switch (savingsRule.getRuleType()) {
            case GUILTYPLEASURE:

                for (Transaction transaction : transactions) {
                    if (transaction.getAmount() < 0) {
                        count++;

                        log.info("Transaction with id: " + transaction.getId() +
                                " is of type debit and will result in a savings event.");

                        Validate.notNull(transaction);
                        for (Long savingsGoalId : savingsRule.getSavingsGoalIds()) {

                            Validate.notNull(savingsGoalId);

                            if (savingsGoalId == null) {
                                savingsGoalId = count;
                            }


                            if (transaction.getDescription().equals("Starbucks")) {

                                newSavings = SavingsEvent.builder()
                                        .transaction(transaction)
                                        .savingsRuleId(savingsRule.getId())
                                        .savingsGoalId(savingsGoalId)
                                        .eventName(SavingsEvent.EventName.rule_application)
                                        .ruleType(savingsRule.getRuleType())
                                        .build();

                                presentSavings += Math.abs(transaction.getAmount());
//                                presentSavings += Math.abs(newSavings.getAmount()); //these should be equal

                                savingsRule.addSavingsGoal(savingsGoalId);
                                Validate.notNull(newSavings);
                                listOfSavingsEvents.add(newSavings);

                                log.info("guilty savings added in dollars: " + newSavings.getAmount());


                            } else {
                                continue;
                            }

                        }

                    } else {
                        log.info("Transaction with id: " + transaction.getId() +
                                " is of type credit, thus no savings event.");
                    }

                }
                break;

            case ROUNDUP:

                for (Transaction transaction : transactions) {
                    if (transaction.getAmount() < 0) {

                        log.info("Transaction with id: " + transaction.getId() +
                                " is of type debit and will result in a savings event.");

                        Validate.notNull(transaction);
                        for (Long savingsGoalId : savingsRule.getSavingsGoalIds()) {
                            Validate.notNull(savingsGoalId);

                            BigDecimal bigDecimal = BigDecimal.valueOf(transaction.getAmount());
                            log.info("bigDecimal=" + bigDecimal);
                            bigDecimal = bigDecimal.setScale(0, RoundingMode.UP);
                            log.info("new value of bigDecimal is: " + bigDecimal);

                            BigDecimal bd; // the value you get
                            double adjustedAmount = bigDecimal.doubleValue(); // The double you want
                            log.info("after rounding amount = " + adjustedAmount);


                            if (adjustedAmount % 2 != 0) {
                                adjustedAmount = adjustedAmount + 1;

                            } else {
                                adjustedAmount = adjustedAmount;
                            }

                            log.info("after rounding and checking, amount = " + adjustedAmount);

                            double savedDifference = adjustedAmount - transaction.getAmount();

                            newSavings = SavingsEvent.builder()
                                    .transaction(transaction)
                                    .savingsRuleId(savingsRule.getId())
                                    .savingsGoalId(savingsGoalId)
                                    .eventName(SavingsEvent.EventName.rule_application)
                                    .ruleType(savingsRule.getRuleType())
                                    .build();

                            newSavings.setAmount(savedDifference);
                            log.info("roundup savings added in dollars: " + newSavings.getAmount());


                        }

                    }
                }

                break;

            default:
                log.info("An invalid savings rule type was selected");
                return null;

        }
        return listOfSavingsEvents;

    }
}
