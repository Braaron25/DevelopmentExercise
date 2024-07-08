package com.neoris.micro_two.controller;

import com.neoris.micro_two.dto.AccountRQ;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Value("${URL.MICRO_ONE}")
    private String url;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createAccountSuccess(){
        AccountRQ accountRQ = new AccountRQ();

        accountRQ.setType("Ahorros");
        accountRQ.setState(true);
        accountRQ.setClientIdentification("1719870808");
        accountRQ.setInitialBalance(BigDecimal.valueOf(500));

        webTestClient.post()
                .uri("/api/v1/cuenta")
                .bodyValue(accountRQ)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.response.accountNumber")
                .exists();
    }

    @Test
    void createAccountError(){
        AccountRQ accountRQ = new AccountRQ();

        accountRQ.setType("Ahorros");
        accountRQ.setState(true);
        accountRQ.setClientIdentification("1719870809");
        accountRQ.setInitialBalance(BigDecimal.valueOf(500));

        webTestClient.post()
                .uri("/api/v1/cuenta")
                .bodyValue(accountRQ)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("500 Internal Server Error from GET http://"+url+"/api/v1/cliente");
    }

}