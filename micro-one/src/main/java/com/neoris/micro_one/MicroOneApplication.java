package com.neoris.micro_one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class MicroOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroOneApplication.class, args);
	}

}
