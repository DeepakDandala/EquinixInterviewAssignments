Feature: Verify the generate OTP API

  @smoke @sanity @regression @GenerateOTPAPI
  Scenario: Send a valid request
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | application/json   |
      | Content-Type     | application/json   |
    And I pass a valid payload to the generateOTPAPI
    And I hit the generateOTPAPI endpoint
    Then The response code should be 200
    And The response should have these headers with corresponding values
      | responseHeaderKeys | responseHeaderValues            |
      | content-type       | application/json; charset=utf-8 |
    And The response body should contain these keys
      | responseBodyKeys |
      | txnId            |

  @sanity @regression @GenerateOTPAPI
  Scenario: Send a request with invalid phone number
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | application/json   |
      | Content-Type     | application/json   |
    And I pass a payload containing an invalid phone number to the generateOTPAPI
    And I hit the generateOTPAPI endpoint
    Then The response code should be 400
    And The response should have these headers with corresponding values
      | responseHeaderKeys | responseHeaderValues            |
      | content-type       | application/json; charset=utf-8 |
    And The response body should contain these keys
      | responseBodyKeys |
      | errorCode        |
    And The response should body contain these keys with corresponding values
      | responseBodyKeys | responseBodyValues      |
      | error            | Input parameter missing |

  @sanity @regression @GenerateOTPAPI
  Scenario: Send a request without phone number
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | application/json   |
      | Content-Type     | application/json   |
    And I pass a payload without a phone number to the generateOTPAPI
    And I hit the generateOTPAPI endpoint
    Then The response code should be 400
    And The response should have these headers with corresponding values
      | responseHeaderKeys | responseHeaderValues            |
      | content-type       | application/json; charset=utf-8 |
    And The response body should contain these keys
      | responseBodyKeys |
      | errorCode        |
    And The response should body contain these keys with corresponding values
      | responseBodyKeys | responseBodyValues      |
      | error            | Input parameter missing |

  @regression @GenerateOTPAPI
  Scenario: Send a request without accept header
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | Content-Type     | application/json   |
    And I pass a valid payload to the generateOTPAPI
    And I hit the generateOTPAPI endpoint
    Then The response code should be 400

  @regression @GenerateOTPAPI
  Scenario: Send a request without Content-Type header
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | application/json   |
    And I pass a valid payload to the generateOTPAPI
    And I hit the generateOTPAPI endpoint
    Then The response code should be 400

  @regression @GenerateOTPAPI
  Scenario: Send a request with invalid accept and Content-Type headers
    When I set these headers
      | requestHeaderKey | requestHeaderValue |
      | accept           | text/plain         |
      | Content-Type     | text/plain         |
    And I pass a valid payload to the generateOTPAPI
    And I hit the generateOTPAPI endpoint
    Then The response code should be 400
