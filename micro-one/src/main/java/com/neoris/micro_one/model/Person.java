package com.neoris.micro_one.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private Integer id;

    @Column( "name")
    private String name;

    @Column("gender")
    private String gender;

    @Column("age")
    private Integer age;

    @Column("identification")
    private String identification;

    @Column("direction")
    private String direction;

    @Column("phone")
    private String phone;
}
