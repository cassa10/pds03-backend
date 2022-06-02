Feature: Delete subject

  Scenario: Delete a subject from a degree
    Given A new degree with two subjects
    When Deleted a subject
    Then Should be collection with 1 subject

