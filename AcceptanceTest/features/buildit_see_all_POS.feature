Feature:Site Engineer

  I want to approve PO as siteEngineer

  Scenario: Login to buildit and create PO, approve
    Given I open the build it page
    When I am typing username request "site-engineer" on build it
    Then I am typing password request "password" on build it
    Then clicking login button    
    Then I open the POs page
   
    
  


  



