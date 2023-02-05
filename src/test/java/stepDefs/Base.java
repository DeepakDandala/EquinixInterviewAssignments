package stepDefs;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import io.cucumber.java.BeforeAll;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Base {
	
	public RequestSpecification request;
	public Response response;
	public static HashMap<String, HashMap<String, String>> apiDetails;
	
	@BeforeAll
	public static void beforeAll() {
		baseURI = "https://cdn-api.co-vin.in/api";
		basePath = "/v2";
	}
	
}
