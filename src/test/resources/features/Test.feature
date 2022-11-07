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
#    Then a trade occurs with the price of "100" and quantity of "50"
    Then a trade for sell order "1" and buy order "0" occurs with the price of "100" and quantity of "50"

  Scenario: First scenario
    Given I create user "Diamond" with password "pass1"
    And I create user "Paper" with password "pass2"
    And I create user "Pen" with password "pass3"
    And I create user "Notebook" with password "pass4"
    And I create security "WSB"
    And I create security "WSC"
    When User "Diamond" puts a "buy" order for security "WSB" with a price of "101" and quantity of "50"
    When User "Pen" puts a "buy" order for security "WSB" with a price of "101" and quantity of "50"
    When User "Notebook" puts a "buy" order for security "WSB" with a price of "102" and quantity of "50"
    When User "Notebook" puts a "buy" order for security "WSC" with a price of "102" and quantity of "50"
    And User "Paper" puts a "sell" order for security "WSB" with a price of "100" and quantity of "150"
    Then a trade for sell order "4" and buy order "0" occurs with the price of "100" and quantity of "50"
