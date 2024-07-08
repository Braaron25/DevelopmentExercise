package com.neoris.micro_one.controller;


import com.neoris.micro_one.dto.Response;
import com.neoris.micro_one.exception.DataException;
import com.neoris.micro_one.model.Client;
import com.neoris.micro_one.service.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/cliente")
@Slf4j
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    public Mono<ResponseEntity<Response>>  createClient(@RequestBody Client client) {
        return clientService.createClient(client)
                .flatMap(c -> Mono.just(ResponseEntity.ok(Response.builder().response(c).build())))
                .onErrorResume(e -> {
                    if(e.getClass().equals(DataException.class)){
                        return Mono.just(ResponseEntity.badRequest().body(Response.builder().message(e.getMessage()).build()));
                    }else{
                        return Mono.just(ResponseEntity.internalServerError().body(
                                Response.builder().message(e.getMessage()).build()));
                    }
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Response>> getClientByIdentification(@RequestParam String identification) {
        return clientService.getClientByIdentification(identification)
                .flatMap(c  -> Mono.just(ResponseEntity.ok(Response.builder().response(c).build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Response.builder().message(e.getMessage()).build())));
    }

    @GetMapping(value = "/{identification}/reporte")
    public Mono<ResponseEntity<Response>> getClientReport(@PathVariable String identification, @RequestParam String startDate, @RequestParam String endDate) {
        return clientService.generateReport(identification, startDate, endDate)
                .flatMap(c -> Mono.just(ResponseEntity.ok(Response.builder().response(c).build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Response.builder().message(e.getMessage()).build())));
    }

    @PutMapping
    public Mono<ResponseEntity<Response>> updateClient(@RequestBody Client updateClient, @RequestParam Integer id) {
        return clientService.updateClient(id,updateClient)
                .flatMap(c -> Mono.just(ResponseEntity.ok(Response.builder().response(c).build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Response.builder().message(e.getMessage()).build())));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Response>> deleteClient(@RequestParam Integer id) {
        return clientService.deleteClient(id)
                .flatMap(c -> Mono.just(ResponseEntity.ok(Response.builder().message("Cliente eliminado").build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(
                        Response.builder().message(e.getMessage()).build())));
    }

    @GetMapping(value = "/all")
    public Mono<ResponseEntity<Flux<Client>>> getAllClients() {
        return Mono.just(ResponseEntity.ok(clientService.getAllClients()));
    }
}
