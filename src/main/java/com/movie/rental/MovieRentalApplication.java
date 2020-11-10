package com.movie.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieRentalApplication {
	public static void main(final String[] args) {
		SpringApplication.run(MovieRentalApplication.class, args);
	}
}
