package com.demo.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
	@LocalServerPort
	private Integer port;

	String requestBody = """
			{
				    "name": "iPhone15",
				    "description": "This is an iPhone 15",
				    "price": 1000
			}
			""";

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone15"))
				.body("description", Matchers.equalTo("This is an iPhone 15"))
				.body("price", Matchers.equalTo(1000));
	}

	@Test
	 void shouldGetAllProducts() {

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201);

		RestAssured.given()
				.when()
				.get("/api/product")
				.then()
				.statusCode(200)
				.body("$",Matchers.not(Matchers.empty()))
				.body("[0].id",Matchers.notNullValue())
				.body("[0].name", Matchers.equalTo("iPhone15"))
				.body("[0].description", Matchers.equalTo("This is an iPhone 15"))
				.body("[0].price", Matchers.equalTo(1000));
	}

}
