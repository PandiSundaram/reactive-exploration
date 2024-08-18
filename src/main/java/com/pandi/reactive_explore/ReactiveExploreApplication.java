package com.pandi.reactive_explore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.pandi.reactive_explore.sec08")
@EnableR2dbcRepositories(basePackages = "com.pandi.reactive_explore.sec08")
public class ReactiveExploreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveExploreApplication.class, args);
	}

}
