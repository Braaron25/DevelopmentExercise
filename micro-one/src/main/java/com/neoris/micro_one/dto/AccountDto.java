package com.neoris.micro_one.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {

    private Integer accountNumber;
    private String accountType;
    private String state;
    private String balance;
    private List<TransactionDto> transactions;
}
