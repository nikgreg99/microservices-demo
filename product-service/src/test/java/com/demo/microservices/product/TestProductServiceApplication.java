package com.demo.microservices.product;

import org.springframework.boot.SpringApplication;

public class TestProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductServiceApplication::main).with(TestContainersConfiguration.class).run(args);
	}

}
