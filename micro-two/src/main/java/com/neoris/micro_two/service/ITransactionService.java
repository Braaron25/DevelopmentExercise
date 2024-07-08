package com.neoris.micro_two.service;

import com.neoris.micro_two.dto.TransactionRQ;
import com.neoris.micro_two.model.Transaction;
import reactor.core.publisher.Mono;

public interface ITransactionService {

    Mono<Transaction> addTransaction(Mono<TransactionRQ> newTransaction);

    Mono<Transaction> getTransactionById(Integer id);
}
