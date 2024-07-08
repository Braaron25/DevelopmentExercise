package com.neoris.micro_one.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDto {
    private Integer transactionId;
    private Date date;
    private String type;
    private BigDecimal initialBalance;
    private BigDecimal finalBalance;
    private BigDecimal value;
    private String transactionDescription;
}
