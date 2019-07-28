Feature:Reject PO

  I want to dispatch PO 

  Scenario: Login to rentit
    Given I open the rent it page
    When I am typing my username request "clerk1" on rentit
    Then I am typing my password request "clerk1" on rentit
    Then I am clicking login button    
    Then I am open the Plant listining page
    Then I click dispatch plant 
 
   
