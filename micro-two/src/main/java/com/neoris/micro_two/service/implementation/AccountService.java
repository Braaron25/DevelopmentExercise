package com.neoris.micro_two.service.implementation;

import com.neoris.micro_two.dao.AccountRepository;
import com.neoris.micro_two.dao.TransactionRepository;
import com.neoris.micro_two.dto.AccountRQ;
import com.neoris.micro_two.dto.Response;
import com.neoris.micro_two.exception.DataException;
import com.neoris.micro_two.model.Account;
import com.neoris.micro_two.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final TransactionRepository transactionRepository;

    private final WebClient webClient;

    private final AccountRepository accountRepository;


    @Transactional
    public Mono<Account> createAccount(AccountRQ accountRQ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("identification", accountRQ.getClientIdentification());
        return webClient.get().uri("/cliente?identification={identification}", params)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnError(Mono::error)
                .flatMap(res -> {
                    if(res.getResponse() == null){
                        return Mono.error(new DataException("No se encontro el cliente"));
                    }
                    Account account = new Account();
                    account.setAccountType(accountRQ.getType());
                    account.setBalance(accountRQ.getInitialBalance());
                    account.setState(accountRQ.getState());
                    account.setClientId((Integer) ((LinkedHashMap)res.getResponse()).get("id"));
                    return accountRepository.save(account);
                })
                .doOnError(Mono::error);
    }

    public Mono<Account> getAccount(Long accountNumber){
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new DataException("No se encontro la cuenta")))
                .flatMap( account -> transactionRepository.findByAccountNumber(account.getAccountNumber()).distinct().collectList()
                        .map( transactions -> {
                            account.setTransactions(transactions);
                            return account;
                        }));
    }

    @Transactional
    public Mono<Account> deleteAccount(Long accountNumber){
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new DataException("No se encontro la cuenta")))
                .flatMap( a -> accountRepository.delete(a).thenReturn(a));
    }

    @Transactional
    public Mono<Account> updateAccount(Long accountNumber, Account account){
        return accountRepository.findById(accountNumber)
                .switchIfEmpty(Mono.error(new DataException("No se encontro la cuenta")))
                .flatMap( a -> {
                    a.setAccountType(account.getAccountType());
                    a.setState(account.getState());
                    return accountRepository.save(a);
                });
    }

    public Flux<Account> getAccountsByCliendId(Integer cliendId, String start, String end) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(start);
        Date endDate = dateFormat.parse(end);
        return accountRepository.findByClientId(cliendId)
                .switchIfEmpty(Mono.error(new DataException("No se encontro la cuenta")))
                .map( account -> transactionRepository.findByAccountNumberAndDateBetween(account.getAccountNumber(), startDate,endDate).distinct().collectList()
                                .map(transactions -> {
                                    account.setTransactions(transactions);
                                    return account;
                                }))
                .flatMap(x -> x);
    }
}
