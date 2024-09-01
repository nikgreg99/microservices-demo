package com.microservices.demo.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	static {
		mySQLContainer.start();
	}

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}


	@Test
	public void shouldProductBeInStock(){
			var response = RestAssured.given()
					.when()
					.get("/api/inventory?skuCode=iphone_15&quantity=1")
					.then()
					.log().all()
					.statusCode(200)
					.extract().response().as(Boolean.class);

		Assertions.assertTrue(response);


		var negativeResponse = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iphone_15&quantity=1000")
				.then()
				.log().all()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		Assertions.assertFalse(negativeResponse);
	}
}
