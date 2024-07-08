package com.neoris.micro_one.model;

import com.neoris.micro_one.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "client")
@AllArgsConstructor
public class Client extends Person{

    @Column("client_Id")
    private String clientId;

    @Column("password")
    private String password;

    @Column("state")
    private Boolean state;

    @Transient
    private List<AccountDto> accountList;

}
