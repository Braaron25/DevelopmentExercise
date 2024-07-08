package com.neoris.micro_two.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "account")
@AllArgsConstructor
public class Account {

    @Id
    @Column("account_number")
    private Long accountNumber;

    @Column("account_type")
    private String accountType;

    @Column("balance")
    private BigDecimal balance;

    @Column("state")
    private Boolean state;

    @Column("client_id")
    private Integer clientId;

    @Transient
    private List<Transaction> transactions;

}
