package com.paserafim.trivago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TrivagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrivagoApplication.class, args);
	}

}
