Feature:Create PO

  I want to  create PO 

  Scenario: Login to rentit and create PO
    Given I open the rent it page
    When I am typing my username request "clerk1" on rentit
    Then I am typing my password request "clerk1" on rentit
    Then I am clicking login button
    Then I am typing plant request "mini" on rentit
    Then I am selected  startDate
    Then I am selected  startDate day
    Then I am selected  endDate
    Then I am selected  endDate day
    Then I am submit query
    Then select plant for request
    
   


 
