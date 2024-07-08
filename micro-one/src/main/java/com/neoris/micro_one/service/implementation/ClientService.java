package com.neoris.micro_one.service.implementation;


import com.neoris.micro_one.dao.ClientRepository;
import com.neoris.micro_one.dto.AccountDto;
import com.neoris.micro_one.exception.DataException;
import com.neoris.micro_one.model.Client;
import com.neoris.micro_one.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    private final WebClient webClient;


    public Mono<Client> createClient(Client client){
        return clientRepository.findByIdentification(client.getIdentification())
                .hasElement().flatMap(c -> {
            if (c){
                return Mono.error(new DataException("Usuario con la cédula ".concat(client.getIdentification()).concat(" ya existe")));
            } else {
                client.setClientId(UUID.randomUUID().toString());
                return clientRepository.save(client);
            }
        });
    }

    public Mono<Client> getClientByIdentification(String identification) {
        return clientRepository.findByIdentification(identification)
                .switchIfEmpty(Mono.error(new DataException("El usuario no se encontro")));
    }

    public Mono<Client> updateClient(Integer id, Client updateClient) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DataException("El usuario no se encontro")))
                .flatMap(client -> {
                    client.setName(updateClient.getName());
                    client.setPhone(updateClient.getPhone());
                    client.setDirection(updateClient.getDirection());
                    client.setAge(updateClient.getAge());
                    client.setGender(updateClient.getGender());
                    client.setState(updateClient.getState());
                    return clientRepository.save(client);
                });
    }

    public Mono<Boolean> deleteClient(Integer id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DataException("El usuario no se encontro")))
                .flatMap( c -> clientRepository.delete(c).thenReturn(true));
    }

    public Flux<Client> getAllClients() {
        return clientRepository.findAll().switchIfEmpty(Flux.error(new DataException("No existen usuarios")));
    }

    public Mono<Client> generateReport(String identification, String start, String end) {
        return clientRepository.findByIdentification(identification)
                .switchIfEmpty(Mono.error(new DataException("El usuario no se encontro")))
                .flatMap(client -> {
                    client.setAccountList(new ArrayList<>());
                    Map<String, Object> params = new HashMap<>();
                    params.put("clientId", client.getId());
                    params.put("startDate", start);
                    params.put("endDate", end);
                    return webClient.get().uri("/cuenta/{clientId}?startDate={startDate}&endDate={endDate}", params)
                            .retrieve()
                            .bodyToFlux(AccountDto.class)
                            .distinct()
                            .collectList()
                            .map(x -> {
                                client.setAccountList(x);
                                return client;
                            });
                });
    }
}
