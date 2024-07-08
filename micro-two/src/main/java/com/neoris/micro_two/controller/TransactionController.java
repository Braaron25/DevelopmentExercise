package com.neoris.micro_two.controller;

import com.neoris.micro_two.dto.Response;
import com.neoris.micro_two.dto.TransactionRQ;
import com.neoris.micro_two.exception.DataException;
import com.neoris.micro_two.exception.TransactionException;
import com.neoris.micro_two.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movimientos")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping
    public Mono<ResponseEntity<Mono<Response>>> createTransaction(@RequestBody TransactionRQ transactionRQ) {
        return transactionService.addTransaction(Mono.just(transactionRQ))
                .map(res -> ResponseEntity.ok(Mono.just(
                        Response.builder().response(res).build())))
                .onErrorResume(e -> {
                    if (e instanceof DataException || e instanceof TransactionException) {
                        return Mono.just(ResponseEntity.badRequest().body(
                                Mono.just(Response.builder().message(e.getMessage()).build())));
                    } else {
                        return Mono.just(ResponseEntity.internalServerError().body(
                                Mono.just(Response.builder().message(e.getMessage()).build())));
                    }
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Mono<Response>>> getTransaction(@RequestParam Integer transactionId) {
        log.info("Test");
        return transactionService.getTransactionById(transactionId)
                .map( trans -> ResponseEntity.ok(Mono.just(Response.builder().response(trans).build())))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(Mono.just(Response.builder().message("No se encontro la transacción").build()))))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Mono.just(Response.builder().message(e.getMessage()).build()))));
    }
}
