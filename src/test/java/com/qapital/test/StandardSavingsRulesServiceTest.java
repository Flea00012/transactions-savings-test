package com.qapital.test;

import com.qapital.bankdata.transaction.Transaction;
import com.qapital.bankdata.transaction.TransactionsService;
import com.qapital.savings.rule.SavingsRulesService;
import com.qapital.savings.rule.StandardSavingsRulesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


public class StandardSavingsRulesServiceTest {

    @Autowired
    private final SavingsRulesService;

    @Before
    public void setUp() throws Exception {



    }

    @Test
    public void given_LatestTransactionsLoaded_then_ApplySavingsRules() {



    }
}