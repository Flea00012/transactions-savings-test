package com.qapital.test;

import com.qapital.bankdata.transaction.StandardTransactionsService;
import com.qapital.savings.rule.SavingsRule;
import com.qapital.savings.rule.SavingsRulesService;
import com.qapital.savings.rule.StandardSavingsRulesService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StandardSavingsRulesServiceTest {

    private SavingsRulesService savingsRulesService;

    public StandardSavingsRulesServiceTest() {
        savingsRulesService = null;
    }







    @Test
    public void given_LatestTransactionsLoaded_then_ApplySavingsRules() {
        savingsRulesService = new StandardSavingsRulesService(new StandardTransactionsService());

        savingsRulesService.executeRule(new SavingsRule());
    }

    @Test(expected = NullPointerException.class)
    public void given_NullTransactions_then_ExecuteMethodReturnsNull() {
        savingsRulesService = new StandardSavingsRulesService(null);
        savingsRulesService.executeRule(new SavingsRule());


    }
}