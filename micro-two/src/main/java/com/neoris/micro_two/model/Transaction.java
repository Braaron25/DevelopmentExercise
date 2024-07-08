package com.neoris.micro_two.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "transaction")
@AllArgsConstructor
public class Transaction {

    @Id
    @Column("transaction_id")
    private Integer transactionId;

    @Column("date")
    private LocalDateTime date;

    @Column("type")
    private String type;

    @Column("value")
    private BigDecimal value;

    @Column("initial_balance")
    private BigDecimal initialBalance;

    @Column("final_balance")
    private BigDecimal finalBalance;

    @Column("transaction_description")
    private String transactionDescription;

    @Column("account_number")
    private Long accountNumber;

}
