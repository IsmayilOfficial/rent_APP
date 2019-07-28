const {Given, When, Then} = require('cucumber');
const Role = require('testcafe').Role;
const buildpage = require('../support/pages/buildit-page');

Given('I open the build it page', async function() {
    await testController.navigateTo(buildpage.buildit.url());
});
Given('I open the POs page', async function() {
    await testController.navigateTo(buildpage.buildit.urlPOs());
});
When('I am typing username request {string} on build it', async function(text) {
    await testController.typeText(buildpage.buildit.login_text(), text);
});
Then('I am typing password request {string} on build it', async function(text) {
    await testController.typeText(buildpage.buildit.login_pass(), text);
});
Then('clicking login button', async function() {
    await testController.click(buildpage.buildit.loginButton() );


});


Then('I am typing  request {string} on buildit', async function(text) {
    await testController.typeText(buildpage.buildit.Mini(), text );


});
Then('select  startDate', async function() {
    await testController.click(buildpage.buildit.startDate());
});
Then('select startDate day', async function() {
    await testController.click(buildpage.buildit.startDateselectday());



});

Then('select  endDate', async function() {
    await testController.click(buildpage.buildit.endDate());



});
Then('select endDate day', async function() {
    await testController.click(buildpage.buildit.endDateselectday());



});
Then('submit the query',async function() {
    await testController.click(buildpage.buildit.submitquery() );
});
Then('select plant for PO',async function() {
    await testController.click(buildpage.buildit.select_plant() );
});
Then('select click create PO button',async function() {
    await testController.click(buildpage.buildit.create_PO() );
});

Then('I open the Plant hire page', async function() {
    await testController.navigateTo(buildpage.buildit.urlPH());
});
Then('I approve the PO', async function() {
    await testController.click(buildpage.buildit.approve_PO());
});
Then('I reject the PO', async function() {
    await testController.click(buildpage.buildit.reject_PO());
});
Then('I want to see status', async function() {
    await testController.click(buildpage.buildit.status_PO());
});
Then('I want to approve Invoice as site Engineer', async function() {
    await testController.click(buildpage.buildit.approve_Invoice());
});
Then('I click the View details see status for more information', async function() {
    await testController.click(buildpage.buildit.view_details_status());
});
Then('I clicking Extend PO', async function() {
    await testController.click(buildpage.buildit.extend());
});
Then('I submit extension', async function() {
    await testController.click(buildpage.buildit.submitEx());
});
Then('I clicking Recect extension PO', async function() {
    await testController.click(buildpage.buildit.recectEx());
});
Then('I clicking Accept extension PO', async function() {
    await testController.click(buildpage.buildit.acceptEx());
});
Then('I want to modify PO ,as clicking button modify', async function() {
    await testController.click(buildpage.buildit.modify());
});


Then('I should see status', async function() {
    await   testController.expect((buildpage.buildit.pending()).innerText).eql('PENDING');
    await   testController.expect((buildpage.buildit.name_POs()).innerText).eql('Mini excavator');
});
