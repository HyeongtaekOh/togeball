package com.ssafy.togeball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TogeballApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogeballApplication.class, args);
	}

}
