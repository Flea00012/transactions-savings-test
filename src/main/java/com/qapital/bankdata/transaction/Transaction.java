package com.qapital.bankdata.transaction;


import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Transaction {

    private Long id;
    private Long userId;
    private Double amount;
    private String description;
    private LocalDate date;



}
