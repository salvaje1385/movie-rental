package com.movie.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.movie.rental"})
@EnableJpaRepositories(basePackages="com.movie.rental.repository")
@EnableTransactionManagement
@EntityScan(basePackages="com.movie.rental.model")
@EnableJpaAuditing
public class MovieRentalApplication {
	public static void main(final String[] args) {
		SpringApplication.run(MovieRentalApplication.class, args);
	}
}
