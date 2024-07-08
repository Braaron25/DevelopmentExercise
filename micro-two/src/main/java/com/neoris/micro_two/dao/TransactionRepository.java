package com.neoris.micro_two.dao;

import com.neoris.micro_two.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {

    Flux<Transaction> findByAccountNumberAndDateBetween(Long accountNumber, Date from, Date to);
    Flux<Transaction> findByAccountNumber(Long accountNumber);
}
