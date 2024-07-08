package com.neoris.micro_two.service;

import com.neoris.micro_two.dto.AccountRQ;
import com.neoris.micro_two.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

public interface IAccountService {

    Mono<Account> createAccount(AccountRQ accountRQ);

    Mono<Account> getAccount(Long accountNumber);

    Mono<Account> deleteAccount(Long accountNumber);

    Mono<Account> updateAccount(Long accountNumber, Account account);

    Flux<Account> getAccountsByCliendId(Integer cliendId, String start, String end) throws ParseException;
}
