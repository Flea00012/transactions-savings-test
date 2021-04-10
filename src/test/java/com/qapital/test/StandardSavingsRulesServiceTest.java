package com.qapital.test;

import com.qapital.bankdata.transaction.StandardTransactionsService;
import com.qapital.bankdata.transaction.TransactionsService;
import com.qapital.savings.event.SavingsEvent;
import com.qapital.savings.rule.SavingsRule;
import com.qapital.savings.rule.SavingsRulesService;
import com.qapital.savings.rule.StandardSavingsRulesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan(basePackages = {"com.qapital","com.qapital.savings.rule"})
@SpringBootTest
public class StandardSavingsRulesServiceTest {


    private SavingsRulesService savingsRulesService;

    private TransactionsService transactionsService;

    @Before
    public void setUp() {
        transactionsService = new StandardTransactionsService();
        savingsRulesService = new StandardSavingsRulesService(transactionsService);
    }

    @Ignore
    @Test
    public void given_latestTransactionsAreLoaded_then_applySavingsRules() {

        Long userId = 1001L;

        transactionsService.latestTransactionsForUser(userId);
        SavingsRule savingsRule = new SavingsRule();

        savingsRulesService.activeRulesForUser(userId);

        List<SavingsEvent> savingsEvent = savingsRulesService.executeRule(savingsRule);


        //debug to find Null Pointer Exception
        Assert.assertEquals(userId, savingsRule.getUserId());


    }

    @Test(expected = NullPointerException.class)
    public void given_NullTransactions_then_ExecuteMethodReturnsNull() {
        savingsRulesService = new StandardSavingsRulesService(null);
        savingsRulesService.executeRule(new SavingsRule());

    }


    @Test(expected = NullPointerException.class)
    public void given_NullSavingsRule_then_ExecuteMethodReturnsNull() {
        TransactionsService transactionsService = new StandardTransactionsService();
        savingsRulesService = new StandardSavingsRulesService(transactionsService);
        savingsRulesService.executeRule(null);

    }
}