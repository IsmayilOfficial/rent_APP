Feature:Invoice

  Supplier submit Invoice . Site engineer approve Invoice

  Scenario:  creating  PO
    Given I open the build it page
    When I am typing username request "site-engineer" on build it
    Then I am typing password request "password" on build it
    Then clicking login button
    Then I am typing  request "mini" on buildit
    Then  select  startDate
    Then select startDate day
    Then select  endDate
    Then select endDate day
    Then submit the query
    Then select plant for PO
    Then select click create PO button
Scenario: approve PO
    Given I open the build it page
    When I am typing username request "site-engineer" on build it
    Then I am typing password request "password" on build it
    Then clicking login button    
    Then I open the Plant hire page
    Then I approve the PO
 Scenario: Accept PO
    Given I open the rent it page
    When I am typing my username request "clerk1" on rentit
    Then I am typing my password request "clerk1" on rentit
    Then I am clicking login button    
    Then I am open the Plant Order page 
    Then I want to accept PO
 Scenario: Submit Invoice 
    Given I open the rent it page
    When I am typing my username request "clerk1" on rentit
    Then I am typing my password request "clerk1" on rentit
    Then I am clicking login button    
    Then I am open the Plant Order page   
    Then I want to submit Invoice modified PO
Scenario: Approve Invoice 
    Given I open the build it page
    When I am typing username request "site-engineer" on build it
    Then I am typing password request "password" on build it
    Then clicking login button 
    Then I open the Plant hire page   
    Then I want to approve Invoice as site Engineer