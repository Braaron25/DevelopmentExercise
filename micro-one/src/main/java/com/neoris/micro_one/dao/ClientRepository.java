package com.neoris.micro_one.dao;

import com.neoris.micro_one.model.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client, Integer> {

    Mono<Client> findByIdentification(String identification);
}
