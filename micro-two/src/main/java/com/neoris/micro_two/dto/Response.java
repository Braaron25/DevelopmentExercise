package com.neoris.micro_two.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    private String message;
    private Object response;
}
