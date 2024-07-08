package com.neoris.micro_one.service;

import com.neoris.micro_one.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {

    Mono<Client> createClient(Client client);

    Mono<Client> getClientByIdentification(String identification);

    Mono<Client> updateClient(Integer id, Client updateClient);

    Mono<Boolean> deleteClient(Integer id);

    Mono<Client> generateReport(String identification, String start, String end);

    Flux<Client> getAllClients();
}
