

const {Given, When, Then} = require('cucumber');
const Selector = require('testcafe').Selector;
const rentpage = require('../support/pages/rentit-page');


Given(/^I open the rent it page$/, async function() {
    await testController.navigateTo(rentpage.rentit.url());
});
When('I am typing my username request {string} on rentit', async function(text) {
    await testController.typeText(rentpage.rentit.login_text(), text);
});
Then('I am typing my password request {string} on rentit', async function(text) {
    await testController.typeText(rentpage.rentit.login_pass(), text);
});
Then('I am clicking login button', async function() {
    await testController.click(rentpage.rentit.loginButton() );


});


Then('I am typing plant request {string} on rentit', async function(text) {
    await testController.typeText(rentpage.rentit.mini(), text );


});
Then('I am selected  startDate', async function() {
    await testController.click(rentpage.rentit.startDate());



});
Then('I am selected  startDate day', async function() {
    await testController.click(rentpage.rentit.startDateselectday());



});

Then('I am selected  endDate', async function() {
    await testController.click(rentpage.rentit.endDate());



});
Then('I am selected  endDate day', async function() {
    await testController.click(rentpage.rentit.endDateselectday());



});
Then('I am submit query',async function() {
    await testController.click(rentpage.rentit.submitquery() );
});
Then('select plant for request',async function() {
    await testController.click(rentpage.rentit.select_plant() );
});
Then('I am clicking create purchase order',async function() {
    await testController.click(rentpage.rentit.create_PO() );
});
Then('I am open the Plant Order page', async function() {
    await testController.navigateTo(rentpage.rentit.urlPlantOrder());
});
Then('I am open the Plant listining page', async function() {
    await testController.navigateTo(rentpage.rentit.urlDispatch());
});
Then('I click the see detail for more information', async function() {
    await testController.click(rentpage.rentit.details());
});
Then('I want to accept PO', async function() {
    await testController.click(rentpage.rentit.accept());
});
Then('I want to reject PO', async function() {
    await testController.click(rentpage.rentit.reject());
});
Then('I click dispatch plant', async function() {
    await testController.click(rentpage.rentit.dispatch());
});
Then('I want to submit Invoice modified PO', async function() {
    await testController.click(rentpage.rentit.Invoice_PO());
});
Then('I click return plant', async function() {
    await testController.click(rentpage.rentit.dispatch());
});