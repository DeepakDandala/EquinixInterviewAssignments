package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.GenerateOTP;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetStatesStepDefs {
	
	private RequestSpecification request;
	private String endpoint;
	private Response response;
	private GenerateOTP obj;
	private Gson gson = new GsonBuilder().create();
	
	@Before("@GetStatesAPI")
	public void beforeGenerateOTP() {
		endpoint = "/v2/admin/location/states";
		request = given().log().all();
	}
	
	@When("^I send a valid request to the getStatesAPI with (.*)$")
	public void i_send_a_valid_request_to_the_get_states_api(String language) {
		response = request.header("accept","application/json").header("Accept-Language",language)
				.when().get(endpoint);
	}
	
	@Then("^The successful getStatesAPI response code should be (.*)$")
	public void the_successful_get_states_api_response_code_should_be(Integer int1) {
		Assert.assertTrue("Expected "+int1+" but received "+response.getStatusCode(),response.getStatusCode()==int1);
	}
	
	@Then("^The successful getSatesAPI content type should be (.*)$")
	public void the_successful_get_sates_api_content_type_should_be(String string) {
		Assert.assertTrue("Expected "+string+" but received "+response.header("content-type"),response.header("content-type").contains(string));
	}
	
	@Then("The successful getSatesAPI response body should have all states")
	public void the_successful_get_sates_api_response_body_should_have_all_states() {
	    response.then().body("states.state_name", hasItems("Assam","Bihar","Delhi"));
	}
	
	@After("@GetStatesAPI")
	public void after(Scenario scenario) {
		scenario.attach(response.asString(), "text/plain", "response");
	}
	
}