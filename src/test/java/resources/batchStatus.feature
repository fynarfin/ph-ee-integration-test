@gov @ext
Feature: Batches API validation test

  Scenario: BS-001,002,003 Batches API with invalid offset, limit and sort value
    Given I have tenant as "paymentbb2"
    When I call the Batches API with invalid offset, limit and sort value expecting status of 400
    Then I should get non empty response
    And I should have "Invalid offset, limit and sort" in response

  Scenario: BS-004,005 Batches API with invalid dateFrom and dateTo value
    Given I have tenant as "paymentbb2"
    When I call the Batches API with invalid dateFrom and dateTo value expecting status of 400
    Then I should get non empty response
    And I should have "Invalid dateFrom and dateTo" in response

  Scenario: BS-007,008,009 Batches API with invalid params value
    Given I have tenant as "paymentbb2"
    When I call the Batches API with invalid registeringInstitutionId,payerFsp and batchId value expecting status of 400
    Then I should get non empty response
    And I should have "Invalid registeringInstitutionId,payerFsp and batchId" in response

