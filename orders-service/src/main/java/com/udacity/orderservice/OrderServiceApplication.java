package com.udacity.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Web Client for the maps (location) API
	 * @param endpoint where to communicate for the maps API
	 * @return created maps endpoint
	 */
	@Bean(name="cars")
	public WebClient webClientMaps(@Value("${vehicle.endpoint}") String endpoint) {
		return WebClient.create(endpoint);
	}
}
