package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.GenerateOTP;

import static io.restassured.RestAssured.*;

import java.util.Map;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GenerateOTPStepDefs {
	
	private RequestSpecification request;
	private String endpoint;
	private Response response;
	private GenerateOTP obj;
	private Gson gson = new GsonBuilder().create();
	
	@Before("@GenerateOTPAPI")
	public void beforeGenerateOTP() {
		endpoint = "/v2/auth/public/generateOTP";
		request = given().log().all();
	}
	
	@When("I pass a valid payload to the generateOTPAPI")
	public void i_pass_a_valid_payload_to_the_generateOTPAPI() {
		obj = new GenerateOTP("9876543210");
		String json = gson.toJson(obj);
		response = request.header("accept","application/json").header("Content-Type","application/json").body(json)
								.when().post(endpoint);
	}
	
	@When("I pass a payload containing an invalid phone number to the generateOTPAPI")
	public void i_pass_a_payload_containing_an_invalid_phone_number_to_the_generateOTPAPI() {
		obj = new GenerateOTP("987654321");
		String json = gson.toJson(obj);
		response = request.header("accept","application/json").header("Content-Type","application/json").body(json)
				.when().post(endpoint);
	}
	
	@When("I pass a payload without a phone number to the generateOTPAPI")
	public void i_pass_a_payload_without_a_phone_number_to_the_generateOTPAPI() {
		obj = new GenerateOTP("");
		String json = gson.toJson(obj);
		response = request.header("accept","application/json").header("Content-Type","application/json").body(json)
				.when().post(endpoint);
	}
	
	@Then("^The response code should be (.*)$")
	public void the_response_code_should_be_expectedResponseCode(Integer int1) {
		Assert.assertTrue("Expected "+int1+" but received "+response.getStatusCode(),response.getStatusCode()==int1);
	}
	
	@Then("^The content type should be (.*)$")
	public void the_content_type_should_be_expectedContentType(String string) {
		Assert.assertTrue("Expected "+string+" but received "+response.header("content-type"),response.header("content-type").contains(string));
	}
	
	@Then("The response body should contain txnId")
	public void the_response_body_should_contain_txnId() {
		Map<String, Object> map = new Gson().fromJson(response.asString(), new TypeToken<Map<String, Object>>(){}.getType());
		Assert.assertTrue("txnId is not found", map.containsKey("txnId"));
	}
	
	@Then("The response body should contain errorCode and error")
	public void the_response_body_should_contain_errorCode_and_error() {
		Map<String, Object> map = new Gson().fromJson(response.asString(), new TypeToken<Map<String, Object>>(){}.getType());
		Assert.assertTrue("errorCode is not found", map.containsKey("errorCode"));
		Assert.assertTrue("error is not found", map.containsKey("error"));
	}
	
	@Then("^The error value should be (.*)$")
	public void the_error_value_should_be_expectedErrorValue(String string) {
		JsonPath jp = response.jsonPath();
		Assert.assertEquals("Expected "+string+" but received "+jp.get("error"),string,(String)jp.get("error"));
	}
	
	@After("@GenerateOTPAPI")
	public void after(Scenario scenario) {
		scenario.attach(response.asString(), "text/plain", "response");
	}
	
}