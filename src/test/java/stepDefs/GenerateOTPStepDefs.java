package stepDefs;

import io.cucumber.java.en.When;
import payloads.GenerateOTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenerateOTPStepDefs {
	
	private String endpoint = "/auth/public/generateOTP";
	private Base base;
	private GenerateOTP obj;
	private Gson gson = new GsonBuilder().create();
	
	public GenerateOTPStepDefs(Base base) {
		this.base = base;
	}
	
	@When("I pass a valid payload to the generateOTPAPI")
	public void i_pass_a_valid_payload_to_the_generateOTPAPI() {
		obj = new GenerateOTP("9876543210");
		String json = gson.toJson(obj);
		base.request = base.request.body(json);
	}
	
	@When("I pass a payload containing an invalid phone number to the generateOTPAPI")
	public void i_pass_a_payload_containing_an_invalid_phone_number_to_the_generateOTPAPI() {
		obj = new GenerateOTP("987654321");
		String json = gson.toJson(obj);
		base.request = base.request.body(json);
	}
	
	@When("I pass a payload without a phone number to the generateOTPAPI")
	public void i_pass_a_payload_without_a_phone_number_to_the_generateOTPAPI() {
		obj = new GenerateOTP("");
		String json = gson.toJson(obj);
		base.request = base.request.body(json);
	}
	
	@When("I hit the generateOTPAPI endpoint")
	public void iHitTheGenerateOTPAPIEndpoint() {
		base.response = base.request.when().post(endpoint);
	}
	
}