package com.neoris.micro_two.dao;

import com.neoris.micro_two.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

    Flux<Account> findByClientId(Integer clientId);

}
