package com.neoris.micro_two.dto;

import lombok.Data;

@Data
public class Client {

    private Integer id;
    private String name;
    private Integer age;
    private String direction;
    private String address;
    private String phone;
    private String clientId;
    private Boolean state;
}
