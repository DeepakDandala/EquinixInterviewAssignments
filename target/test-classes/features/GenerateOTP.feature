Feature: Verify the generate OTP API

  @smoke @sanity @regression @GenerateOTPAPI
  Scenario Outline: Send a valid request
    When I pass a valid payload to the generateOTPAPI
    Then The response code should be <expectedResponseCode>
    And The content type should be <expectedResponseContentType>
    And The response body should contain txnId

    Examples: 
      | expectedResponseCode | expectedResponseContentType |
      |                  200 | application/json            |

  @sanity @regression @GenerateOTPAPI
  Scenario Outline: Send a request with invalid phone number
    When I pass a payload containing an invalid phone number to the generateOTPAPI
    Then The response code should be <expectedResponseCode>
    And The content type should be <expectedResponseContentType>
    And The response body should contain errorCode and error
    And The error value should be <expectedErrorValue>

    Examples: 
      | expectedResponseCode | expectedResponseContentType | expectedErrorCode |
      |                  400 | application/json            | Invalid Mobile    |

  @regression @GenerateOTPAPI
  Scenario Outline: Send a request with no phone number
    When I pass a payload without a phone number to the generateOTPAPI
    Then The response code should be <expectedResponseCode>
    And The content type should be <expectedResponseContentType>
    And The response body should contain errorCode and error
    And The error value should be <expectedErrorValue>

    Examples: 
      | expectedResponseCode | expectedResponseContentType | expectedErrorCode |
      |                  400 | application/json            | Invalid Mobile    |
