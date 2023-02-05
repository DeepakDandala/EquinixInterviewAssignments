Feature: Verify the get states API

  @smoke @sanity @regression @GetStatesAPI
  Scenario Outline: Send a valid request to the get states API
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | application/json   |
    And I set Accept-Language as <languages>
    And I hit the getStatesAPI endpoint
    Then The response code should be 200
    And The response should have these headers with corresponding values
      | responseHeaderKeys | responseHeaderValues            |
      | content-type       | application/json; charset=utf-8 |
    And The below key should contain all these values in the response body
      | responseBodykeyJsonPath | listOfValues                                                                                                                                                                                                                                                                                                                                                                                                                |
      | states.state_name       | Andaman and Nicobar Islands,Andhra Pradesh,Arunachal Pradesh,Assam,Bihar,Chandigarh,Chhattisgarh,Dadra and Nagar Haveli,Daman and Diu,Delhi,Goa,Gujarat,Haryana,Himachal Pradesh,Jammu and Kashmir,Jharkhand,Karnataka,Kerala,Ladakh,Lakshadweep,Madhya Pradesh,Maharashtra,Manipur,Meghalaya,Mizoram,Nagaland,Odisha,Puducherry,Punjab,Rajasthan,Sikkim,Tamil Nadu,Telangana,Tripura,Uttar Pradesh,Uttarakhand,West Bengal |

    Examples: 
      | languages |
      | hi_IN     |
      | en_US     |
      
  @regression @GetStatesAPI
  Scenario: Send a request to the get states API without accept header
    When I set Accept-Language as hi_IN
    And I hit the getStatesAPI endpoint
    Then The response code should be 400
    
  @regression @GetStatesAPI
  Scenario: Send a request to the get states API without Accept-Language header
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | application/json   |
    And I hit the getStatesAPI endpoint
    Then The response code should be 400
    
  @regression @GetStatesAPI
  Scenario: Send a request to the get states API with invalid header
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | text/plain   |
    And I hit the getStatesAPI endpoint
    Then The response code should be 400