#this test for Rent it and Build it 
# for running test 
npm test 

All test related files in Acceptance test folder
https://bitbucket.org/Odeyinka/rentit-project/src/master/AcceptanceTest/
for testing hosts :
Build it - local host 8081
rent it - local host 8080
reference for source code: https://github.com/rquellh/testcafe-cucumber

For a testing we are used:
Cucumber js -  for Gherkin scenarios
TestCafe js -   for simulation browser
Node js - for writing scenarios

For run test first install npm packet 
# npm install
Then you can run test with 
# npm test 
 If you want to run special feature test 
For example 
buildit_modify_acceptExtension.feature 
run this code  in terminal 

# npm test  --specs .\features\buildit_modify_acceptExtension.feature
