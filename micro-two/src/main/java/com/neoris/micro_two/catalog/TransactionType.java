package com.neoris.micro_two.catalog;


public enum TransactionType {
    DEPOSIT("Deposito"),
    WITHDRAWAL("Retiro"),;

    private String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
