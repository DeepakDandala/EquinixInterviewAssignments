package stepDefs;

import io.cucumber.java.en.When;

public class GetStatesStepDefs {
	
	private String endpoint = "/admin/location/states";
	private Base base;
	
	public GetStatesStepDefs(Base base) {
		this.base = base;
	}
	
	@When("^I set Accept-Language as (.*)$")
	public void iSetAcceptLanguage(String language) {
		base.request = base.request.header("Accept-Language",language);
	}
	
	@When("I hit the getStatesAPI endpoint")
	public void iHitTheGetStatesApiEndpoint() {
		base.response = base.request.when().get(endpoint);
	}
	
}