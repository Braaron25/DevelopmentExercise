package com.neoris.micro_two.controller;

import com.neoris.micro_two.dto.AccountRQ;
import com.neoris.micro_two.dto.Response;
import com.neoris.micro_two.exception.DataException;
import com.neoris.micro_two.model.Account;
import com.neoris.micro_two.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/cuenta")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;


    @PostMapping
    public Mono<ResponseEntity<Mono<Response>>> createAccount(@RequestBody AccountRQ account) {
        return accountService.createAccount(account)
                .map(a -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(Response.builder().response(a).build())))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(Mono.just(Response.builder().message("No se pudo crear la cuenta").build()))))
                .onErrorResume(e -> {
                    if(e.getClass().equals(DataException.class)){
                        return Mono.just(ResponseEntity.badRequest().body(Mono.just(Response.builder().message(e.getMessage()).build())));
                    }else{
                        return Mono.just(ResponseEntity.internalServerError().body(
                                Mono.just(Response.builder().message(e.getMessage()).build())));
                    }
                });
    }

    @DeleteMapping
    public Mono<ResponseEntity<Mono<Response>>> deleteAccount(@RequestParam Long accountNumber) {
        return accountService.deleteAccount(accountNumber)
                .map(account -> ResponseEntity.ok(Mono.just(Response.builder().message("Cuenta eliminada").build())))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(Mono.just(Response.builder().message("No se encontro la cuenta").build()))))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Mono.just(Response.builder().message(e.getMessage()).build()))));
    }

    @GetMapping
    public Mono<ResponseEntity<Mono<Response>>> getAccount(@RequestParam Long accountNumber) {
        return accountService.getAccount(accountNumber)
                .map(account -> ResponseEntity.ok(Mono.just(Response.builder().response(account).build())))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(Mono.just(Response.builder().message("No se encontro la cuenta").build()))))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Mono.just(Response.builder().message(e.getMessage()).build()))));
    }

    @PutMapping
    public Mono<ResponseEntity<Mono<Response>>> updateAccount(@RequestBody Account updateAccount, @RequestParam Long accountNumber) {
        return accountService.updateAccount(accountNumber,updateAccount)
                .map(account -> ResponseEntity.ok(Mono.just(Response.builder().response(account).build())))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(Mono.just(Response.builder().message("No se encontro la cuenta").build()))))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Mono.just(Response.builder().message(e.getMessage()).build()))));
    }

    @GetMapping(value = "/{clientId}")
    public Mono<ResponseEntity<Flux<Account>>> getAccountsByClientId(@PathVariable Integer clientId, @RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return Mono.just(ResponseEntity.ok(accountService.getAccountsByCliendId(clientId, startDate, endDate)));
    }
}
