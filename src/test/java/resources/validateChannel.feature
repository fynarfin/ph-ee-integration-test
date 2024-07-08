Feature: Validate Channel Api Request Headers And Body

  Scenario: Error Validity check for Post Transfer API for negative request body
    Given I can create a negative TransactionChannelRequestDTO
    When I call the post transfer API with expected status of 400
    Then I should be able to assert the api validation for negative response

  Scenario: Unsupported header validation for Post Transfer Api Test
    Given I can create an TransactionChannelRequestDTO
    When I call the post transfer API having invalid header with expected status of 400
    Then I should get non empty response
    Then I will assert that response body contains "error.msg.header.validation.errors"

  Scenario: Required header validation for Post Transfer Api Test
    Given I can create an TransactionChannelRequestDTO
    When I call the post transfer API without required header with expected status of 400
    Then I should get non empty response
    Then I will assert that response body contains "error.msg.header.validation.errors"

  Scenario: Error Validity check for Transaction Request API for negative request body
    Given I can create a negative TransactionChannelRequestDTO
    When I call the transaction request API with expected status of 400
    Then I should be able to assert the api validation for negative response

  Scenario: Unsupported header validation for Transaction Request Api Test
    Given I can create an TransactionChannelRequestDTO
    When I call the transaction request API having invalid header with expected status of 400
    Then I should get non empty response
    Then I will assert that response body contains "error.msg.header.validation.errors"

  Scenario: Required header validation for Transaction Request Api Test
    Given I can create an TransactionChannelRequestDTO
    When I call the transaction request API without required header with expected status of 400
    Then I should get non empty response
    Then I will assert that response body contains "error.msg.header.validation.errors"

  Scenario: Error Validity check for GSMA Transaction API for negative request body
    Given I can create a negative GsmaTransfer DTO
    When I call the GSMA transaction API with expected status of 400
    Then I should be able to assert the api validation for negative response

  Scenario: Unsupported header validation for GSMA Transaction Api Test
    Given I can create a GsmaTransfer DTO
    When I call the gsma transaction API having invalid header with expected status of 400
    Then I should get non empty response
    Then I will assert that response body contains "error.msg.header.validation.errors"

  Scenario: Required header validation for Transaction Request Api Test
    Given I can create a GsmaTransfer DTO
    When I call the gsma transaction API without required header with expected status of 400
    Then I should get non empty response
    Then I will assert that response body contains "error.msg.header.validation.errors"