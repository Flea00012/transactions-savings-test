package com.qapital.savings.rule;

import com.qapital.bankdata.transaction.Transaction;
import com.qapital.bankdata.transaction.TransactionsService;
import com.qapital.savings.event.SavingsEvent;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Validate.notNull(savingsRule);
        List<SavingsEvent> listOfSavingsEvents = new ArrayList<>();
        List<Transaction> transactions;

        SavingsEvent newSavings;

        transactions = transactionsService.latestTransactionsForUser(1L);

/*        if(transactions.getDescription == savingsRule.getDescription){
            save(transaction.getAmount());          */

        switch (savingsRule.getRuleType()) {
            case guiltypleasure:

                for (Transaction transaction : transactions) {
                    if (transaction.getAmount() < 0) {

                        System.out.println("Transaction with id: " + transaction.getId() +
                                " is of type debit and will result in a savings event.");

                        Validate.notNull(transaction);
                        for (Long savingsGoalId : savingsRule.getSavingsGoalIds()) {
                            Validate.notNull(savingsGoalId);
                            if (transaction.getDescription().equals("Starbucks")) {
                                newSavings = new SavingsEvent(transaction.getUserId(), savingsGoalId,
                                        savingsRule.getId(), SavingsEvent.EventName.rule_application,
                                        transaction.getDate(), transaction.getAmount(),
                                        savingsRule.getId(), savingsRule);

                                presentSavings += Math.abs(transaction.getAmount());

                                //check this code - might be wrong!!
                                savingsRule.addSavingsGoal(savingsGoalId);
                                Validate.notNull(newSavings);
                                listOfSavingsEvents.add(newSavings);
                                System.out.println("guilty savings add in dollars: " + newSavings.getAmount());

                            } else {
                                continue;
                            }

                        }

                    } else {
                        System.out.println("Transaction with id: " + transaction.getId() +
                                " is of type credit, thus no savings event.");
                    }

                }
                break;

            case roundup:

                for (Transaction transaction : transactions) {
                    if (transaction.getAmount() < 0) {

                        System.out.println("Transaction with id: " + transaction.getId() +
                                " is of type debit and will result in a savings event.");

                        Validate.notNull(transaction);
                        for (Long savingsGoalId : savingsRule.getSavingsGoalIds()) {
                            Validate.notNull(savingsGoalId);

                            double costOfPurchase = Math.abs(transaction.getAmount());

                            long factor = (long) Math.pow(10, 2);

                            costOfPurchase = costOfPurchase * factor;

                            long roundedResult = Math.round(costOfPurchase);

                            double correctedCost = (roundedResult / factor);

                            presentSavings += correctedCost;

                            newSavings = new SavingsEvent(transaction.getUserId(), savingsGoalId,
                                    savingsRule.getId(), SavingsEvent.EventName.rule_application,
                                    transaction.getDate(), transaction.getAmount(),
                                    savingsRule.getId(), savingsRule);

                        }

                    }
                }

                break;

            default:
                System.out.println("An invalid savings rule type was selected");
                return null;

        }
        return listOfSavingsEvents;

    }
}
