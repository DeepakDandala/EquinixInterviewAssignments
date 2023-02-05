Feature: Verify the get states API

  @smoke @sanity @regression @GetStatesAPI
  Scenario Outline: Send a valid request to the get states API
    When I send a valid request to the getStatesAPI with <Accept-Languages>
    Then The successful getStatesAPI response code should be <expectedResponseCode>
    And The successful getSatesAPI content type should be <expectedResponseContentType>
    And The successful getSatesAPI response body should have all states

    Examples: 
      | Accept-Languages | expectedResponseCode | expectedResponseContentType |
      | hi_IN            |                  200 | application/json            |
      | en_US            |                  200 | application/json            |
