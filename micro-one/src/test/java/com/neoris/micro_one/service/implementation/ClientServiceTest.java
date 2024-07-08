package com.neoris.micro_one.service.implementation;

import com.neoris.micro_one.dao.ClientRepository;
import com.neoris.micro_one.exception.DataException;
import com.neoris.micro_one.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private WebClient webClient;

    private ClientService clientService;

    private final String identification = "1719870808";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientService = new ClientService(clientRepository,webClient);
    }

    @Test
    void createClient() {
    }

    @Test
    void testGetClientByIdentification() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findByIdentification(this.identification)).thenReturn(Mono.just(client));

        Mono<Client> clientMono = clientService.getClientByIdentification(this.identification);
        StepVerifier.create(clientMono)
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository, times(1)).findByIdentification(this.identification);
    }

    @Test
    void testGetClientByIdentificationWhenEmpty() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findByIdentification(this.identification)).thenReturn(Mono.empty());

        Mono<Client> clientMono = clientService.getClientByIdentification(this.identification);
        StepVerifier.create(clientMono)
                .expectNextCount(0)
                .expectError()
                .verify();

        verify(clientRepository, times(1)).findByIdentification(this.identification);
    }

    @Test
    void testCreateNewClient() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findByIdentification(this.identification)).thenReturn(Mono.empty());
        when(clientRepository.save(client)).thenReturn(Mono.just(client));

        Mono<Client> result = clientService.createClient(client);

        StepVerifier.create(result)
                .expectNextMatches(savedClient -> savedClient.getIdentification().equals(this.identification))
                .expectComplete()
                .verify();

        Mockito.verify(clientRepository, Mockito.times(1)).save(client);
    }

    @Test
    void testCreateNewClientWhenDuplicated() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findByIdentification(this.identification)).thenReturn(Mono.just(client));

        Mono<Client> result = clientService.createClient(client);

        StepVerifier.create(result)
                .expectError(DataException.class)
                .verify();

        Mockito.verify(clientRepository, Mockito.times(0)).save(client);
    }

    @Test
    void testUpdateClientById() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findById(1)).thenReturn(Mono.just(client));
        when(clientRepository.save(client)).thenReturn(Mono.just(client));

        Mono<Client> clientMono = clientService.updateClient(1,client);
        StepVerifier.create(clientMono)
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testUpdateClientByIdWhenEmpty() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findById(1)).thenReturn(Mono.empty());

        Mono<Client> clientMono = clientService.updateClient(1, client);
        StepVerifier.create(clientMono)
                .expectNextCount(0)
                .expectError()
                .verify();

        verify(clientRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteClientById() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findById(1)).thenReturn(Mono.just(client));
        when(clientRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Boolean> clientMono = clientService.deleteClient(1);
        StepVerifier.create(clientMono)
                .expectNext(true)
                .verifyComplete();

        verify(clientRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void testDeleteClientByIdWhenEmpty() {

        Client client = new Client();
        client.setIdentification(this.identification);

        when(clientRepository.findById(1)).thenReturn(Mono.empty());

        Mono<Boolean> clientMono = clientService.deleteClient(1);
        StepVerifier.create(clientMono)
                .expectNextCount(0)
                .expectError()
                .verify();

        verify(clientRepository, times(1)).findById(1);
    }

}