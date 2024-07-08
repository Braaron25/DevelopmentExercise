package com.neoris.micro_two.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRQ {
    private String type;
    private BigDecimal initialBalance;
    private Boolean state;
    private String clientIdentification;
}
