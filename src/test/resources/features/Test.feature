Feature: Trading System
  Acceptance tests for trading system

  Background:
    Given a trading platform running at host localhost and port 8080

  Scenario: First scenario
    Given I create user "Diamond1" with password "pass1"
    And I create user "Paper" with password "pass2"
    And I create security "WSB1"
