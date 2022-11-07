Feature: Trading System
  Acceptance tests for trading system

  Background:
    Given a trading platform running at host localhost and port 8080

  Scenario: First scenario
    Given I create user "Diamond" with password "pass1"
    And I create user "Paper" with password "pass2"
    And I create security "WSB"
    When User "Diamond" puts a "buy" order for security "WSB" with a price of "101" and quantity of "50"
    And User "Paper" puts a "sell" order for security "WSB" with a price of "100" and quantity of "50"
    Then a trade occurs with the price of "100" and quantity of "50"
