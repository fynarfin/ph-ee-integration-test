
@gov
Feature: Get Txn Req API test

  Scenario: GTX-001 Get Txn Req API Test
    Given I have tenant as "paymentbb2"
    And I call collection api with expected status 200
    When I call the operations-app auth endpoint with username: "mifos" and password: "password"
    Then I should get a valid token
    When I call the get txn API with expected status of 200
    Then I should get non empty response
    And I should have clientCorrelationId in response


  Scenario: GTX-002 Get Txn Req API with Date filter
    Given I have tenant as "paymentbb2"
    When I call the operations-app auth endpoint with username: "mifos" and password: "password"
    Then I should get a valid token
    When I call the get txn API with date today minus 2 day and "current date" with expected status of 200
    Then I should get non empty response
    And I should have startedAt and completedAt in response


  Scenario: GTX-003
    Given I have tenant as "paymentbb2"
    When I call the get txn API for payer party ID 12345 with expected status of 200
    Then I should get non empty response
    And I should have startedAt and completedAt in response

