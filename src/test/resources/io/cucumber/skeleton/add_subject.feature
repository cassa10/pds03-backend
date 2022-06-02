Feature: Add subject

  Scenario: A new degree without subjects
    Given A new degree
    When Get subjects
    Then Should be collection empty

  Scenario: A new degree with subjects
    Given A new degree
    When Added subject
    Then Should be a collection with 1 element
