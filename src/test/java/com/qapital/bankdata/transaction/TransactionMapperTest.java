package com.qapital.bankdata.transaction;

import com.qapital.savings.rule.SavingsRule;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TransactionMapperTest {


    @Test
    public void test() {

        Transaction transaction = new Transaction(1L, 100L, 3.0d, "Starbucks", LocalDate.of(2021, 1, 1));

        SavingsRule savingsRule = TransactionMapper.INSTANCE.transactionToSavingsRule(transaction);

        assertNotNull(savingsRule);


    }
}