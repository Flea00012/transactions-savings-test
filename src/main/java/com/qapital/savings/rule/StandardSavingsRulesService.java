package com.qapital.savings.rule;

import com.qapital.bankdata.transaction.Transaction;
import com.qapital.bankdata.transaction.TransactionsService;
import com.qapital.savings.event.SavingsEvent;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardSavingsRulesService implements SavingsRulesService {


    private final TransactionsService transactionsService;

    @Autowired
    public StandardSavingsRulesService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
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
        Validate.notNull(savingsRule);
        List<SavingsEvent> listOfSavingsEvents = null;
        List<Transaction> transactions = null;

        SavingsEvent newSavings;

        //transactions latest
        transactions = transactionsService.latestTransactionsForUser(1L);


        switch (savingsRule.getRuleType()) {
            case guiltypleasure:

                for (Transaction transaction : transactions) {
                    Validate.notNull(transaction);
                    for (Long savingsGoalId : savingsRule.getSavingsGoalIds()) {
                        Validate.notNull(savingsGoalId);
                        if (transaction.getDescription().equals("Starbucks")) {
                            newSavings = new SavingsEvent(transaction.getUserId(), savingsGoalId,
                                    savingsRule.getId(), SavingsEvent.EventName.rule_application,
                                    transaction.getDate(), transaction.getAmount(),
                                    savingsRule.getId(), savingsRule);

                            //check this code - might be wrong!!
                            savingsRule.addSavingsGoal(savingsGoalId);
                            Validate.notNull(newSavings);
                            listOfSavingsEvents.add(newSavings);

                        } else {
                            return null;
                        }

                    }

                }
                break;

            case roundup:
                // Statements
                break;

            default:
                return null;


            //determine savingsrule


            //divide the savings amount among the SavingsGoalsIds.count()


            //return savings events


        }
        return listOfSavingsEvents;

    }
}
