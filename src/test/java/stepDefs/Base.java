package stepDefs;

import static io.restassured.RestAssured.*;

import io.cucumber.java.BeforeAll;

public class Base {
	
	@BeforeAll
	public static void beforeAll() {
		baseURI = "https://cdn-api.co-vin.in/api";
	}
	
}
