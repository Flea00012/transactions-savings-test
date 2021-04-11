package com.qapital.bankdata.transaction;

import com.qapital.savings.rule.SavingsRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TransactionMapper {


    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = "userId", target = "userId")
    SavingsRule transactionToSavingsRule(Transaction transaction);



}


