package com.neoris.micro_two.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRQ {
    private String type;
    private BigDecimal value;
    private Long accountNumber;
}
