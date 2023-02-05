package stepDefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;

public class CommonStepDefs {
	
	private Base base;
	private Map<String, String> responseBodyAsMap;
	private JsonPath jp;
	private Integer actualInteger;
	private Integer expectedInteger;
	private String actualString;
	private String expectedString;
	private String key;
	
	public CommonStepDefs(Base base) {
		this.base = base;
	}
	
	@Before
	public void beforeGenerateOTP() {
		base.request = given().log().all();
	}
	
	@When("I set these headers")
	public void iSetTheseHeaders(DataTable dataTable) {
		List<Map<String, String>> headers = dataTable.asMaps(String.class, String.class);
		for(Map<String, String> e : headers) {
			base.request = base.request.header(e.get("requestHeaderKey"), e.get("requestHeaderValue"));
		}
	}
	
	@Then("The response code should be {int}")
	public void theResponseCodeShouldBeExpectedResponseCode(Integer expectedResponseCode) {
		actualInteger = (Integer) base.response.getStatusCode();
		expectedInteger = expectedResponseCode;
		Assert.assertTrue("Expected "+expectedInteger+" but received "+actualInteger, actualInteger.equals(expectedInteger));
	}
	
	@Then("The response should have these headers with corresponding values")
	public void theResponseShouldHaveTheseHeadersWithCorrespondingvalues(DataTable dataTable) {
		List<Map<String, String>> headerKeyValuePairs = dataTable.asMaps(String.class, String.class);
		for(Map<String, String> e : headerKeyValuePairs) {
			key = e.get("responseHeaderKeys");
			actualString = base.response.header(key);
			expectedString = e.get("responseHeaderValues");
			Assert.assertEquals("Expected "+expectedString+" but received "+actualString, expectedString, actualString);
		}
	}
	
	@Then("The response body should contain these keys")
	public void theResponseBodyShouldContainTheseKeys(DataTable dataTable) {
		List<Map<String, String>> responseBodyKeysList = dataTable.asMaps(String.class, String.class);
		responseBodyAsMap = new Gson().fromJson(base.response.asString(), new TypeToken<Map<String, String>>(){}.getType());
		for(Map<String, String> e : responseBodyKeysList) {
			Assert.assertTrue(e.get("responseBodyKeys")+" missing from the response", responseBodyAsMap.containsKey(e.get("responseBodyKeys")));
		}
		
	}
	
	@Then("The response should body contain these keys with corresponding values")
	public void theResponseBodyShouldContainTheseKeysWithCorrespondingValues(DataTable dataTable) {
		List<Map<String, String>> bodyKeyValuePairs = dataTable.asMaps(String.class, String.class);
		responseBodyAsMap = new Gson().fromJson(base.response.asString(), new TypeToken<Map<String, String>>(){}.getType());
		for(Map<String, String> e : bodyKeyValuePairs) {
			key = e.get("responseBodyKeys");
			actualString = responseBodyAsMap.get(key);
			expectedString = e.get("responseBodyValues");
			Assert.assertEquals("Expected "+expectedString+" but received "+actualString, expectedString, actualString);
		}
	}
	
	@Then("The response body should contain these values")
	public void theResponseBodyShouldContainTheseValues(String jsonPathOfKey, List<String> values) {
		base.response.then().body(jsonPathOfKey, hasItems(values));
	}
	
	@Then("^The values of the following (.*) should be (.*)$")
	public void theValuesOfTheFollowingKeysShouldBeExpectedValues(List<String> keys, List<String> expectedValues) {
		jp = base.response.jsonPath();
		for(int i=0;i<keys.size();i++) {
			Assert.assertEquals("Expected "+expectedValues.get(i)+" but received "+jp.get(keys.get(i)), expectedValues.get(i), (String)jp.get(keys.get(i)));
		}
	}
	
	@Then("The below key should contain all these values in the response body")
	public void theBelowKeyShouldContainAllTheseValuesInTheResponseBody(DataTable dataTable) {
		List<Map<String, String>> bodyJsonPathValuePairs = dataTable.asMaps(String.class, String.class);
		List<String> act;
		List<String> exp;
		String jp;
		for(Map<String, String> e : bodyJsonPathValuePairs) {
			jp = e.get("responseBodykeyJsonPath");
			exp = Arrays.asList(e.get("listOfValues").split(","));
			act = base.response.jsonPath().get(jp);
			Assert.assertEquals("Value(s) are missing", exp, act);
		}
	}
	
	
	@After
	public void after(Scenario scenario) {
		scenario.attach(base.response.asString(), "text/plain", "response");
	}
	
}