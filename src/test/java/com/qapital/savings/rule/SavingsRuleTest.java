package com.qapital.savings.rule;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.*;
import static org.junit.Assert.*;

public class SavingsRuleTest {

//    @Test(expected = NullPointerException.class)
//    public void givenNullObject_whenCreateSavingsRule_thenReturnNullPointerException(){
//         new SavingsRule((SavingsRule.GuiltyPleasureSavingsRuleBuilder) null);
//
//    }

    @Test
    public void giveGuiltyPleasure_whenBuildingSavingsRule_thenReturnGuiltyPleasure(){

        Stream<Long> s = Stream.of(1L, 2L, 3L, 4L);
//
//        SavingsRuleFactory savingsRule = new SavingsRuleFactory(1L, 1000L, SavingsRule.RuleType.GUILTYPLEASURE,SavingsRule.RuleStatus.ACTIVE)
//                .withPlaceDescription("Gym")
//                .withAmount(3.00d)
//                .withSavingGoalIds(s.collect(Collectors.toList()))
//                .build();
//
//
//        assertEquals(of(3.00d),savingsRule.getAmount());
    }

}