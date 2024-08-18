Feature: Save a client

  Scenario: Create a new client
    Given I have a user with the following details
      | document  | name        | email                   | phone       | deliveryAddress  |
      | CC-123456 | Pedro Perez | pedro.perez22@gmail.com | 311-7779988 | Calle 99 #200-20 |
    When In clients I send a request to "/clients"
    Then The response status should be 201
    And the response body should be
      | document  | name        | email                   | phone       | deliveryAddress  |
      | CC-123456 | Pedro Perez | pedro.perez22@gmail.com | 311-7779988 | Calle 99 #200-20 |

  Scenario: Get client by document
    Given the document "CC-123456"
    When I send a get request to the endpoint "/clients/CC-123456"
    Then the response status should be "200"