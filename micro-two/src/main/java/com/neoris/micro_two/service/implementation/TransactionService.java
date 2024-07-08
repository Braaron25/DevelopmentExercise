package com.neoris.micro_two.service.implementation;

import com.neoris.micro_two.catalog.TransactionType;
import com.neoris.micro_two.dao.AccountRepository;
import com.neoris.micro_two.dao.TransactionRepository;
import com.neoris.micro_two.dto.TransactionRQ;
import com.neoris.micro_two.exception.DataException;
import com.neoris.micro_two.exception.TransactionException;
import com.neoris.micro_two.model.Account;
import com.neoris.micro_two.model.Transaction;
import com.neoris.micro_two.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    public Mono<Transaction> addTransaction(Mono<TransactionRQ> newTransaction) {
        return newTransaction.flatMap(t -> accountRepository.findById(t.getAccountNumber())
                .switchIfEmpty(Mono.error(new DataException("No se encontro la cuenta: ".concat(t.getAccountNumber().toString()))))
                .flatMap( account -> {
                    Transaction transaction = new Transaction();
                    transaction.setInitialBalance(account.getBalance());
                    if(TransactionType.DEPOSIT.getTransactionType().equals(t.getType()) && t.getValue().compareTo(BigDecimal.ZERO) >= 0){
                        depositMoney(account, t);
                    }else if(TransactionType.WITHDRAWAL.getTransactionType().equals(t.getType()) && t.getValue().compareTo(BigDecimal.ZERO) <= 0){
                        withdrawMoney(account, t);
                    }else {
                        return Mono.error(new TransactionException("Tipo de transacción no valida"));
                    }
                    return accountRepository.save(account).flatMap( a -> {
                        transaction.setAccountNumber(a.getAccountNumber());
                        transaction.setType(t.getType());
                        transaction.setValue(t.getValue());
                        transaction.setTransactionDescription(transaction.getType().concat(" de ").concat(transaction.getValue().toString()));
                        transaction.setDate(LocalDateTime.now());
                        transaction.setFinalBalance(account.getBalance());

                        return transactionRepository.save(transaction);
                    });
                }));

    }

    private void depositMoney(Account account, TransactionRQ transaction) {
        account.setBalance(account.getBalance().add(transaction.getValue()));
    }

    private void withdrawMoney(Account account, TransactionRQ transaction) {
        if(account.getBalance().compareTo(transaction.getValue().abs()) < 0){
            throw new TransactionException("Saldo no disponible");
        }
        account.setBalance(account.getBalance().add(transaction.getValue()));
    }

    public Mono<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new DataException("No se encontro la transacción")));
    }
}
