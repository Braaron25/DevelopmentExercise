package com.neoris.micro_two.dto;

import lombok.Data;

@Data
public class ClientResponse {

    private Client client;
    private String error;
}
