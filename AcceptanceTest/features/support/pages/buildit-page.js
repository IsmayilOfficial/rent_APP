const {Selector} = require('testcafe');

// Selectors

function select(selector) {
    return Selector(selector).with({boundTestRun: testController});
}

exports.buildit= {
    urlPH: function() {
        return 'http://localhost:8081/#/planthires';
    },
    urlPOs: function() {
        return 'http://localhost:8081/#/pos';
    },
    url: function() {
        return 'http://localhost:8081/#/login';
    },
    login_text: function() {
        return select('div#app > div > div > div > div > input')},
    login_pass: function() {
        return select("div#app > div > div > div:nth-of-type(2) > div > input")},
    loginButton: function () {

        return select('div#app > div > div > a')
    },
    Mini: function () {
        return select('div#app > div > div > div > section > div > section > div > div > input')

    },

    startDate:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(2) > div > div > div > div > input')

    },
    startDateselectday:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(2) > div > div > div:nth-of-type(3) > div > div > div > section > div > div:nth-of-type(2) > a:nth-of-type(5)')

    },

    endDate:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(3) > div > div > div > div > input')

    },
    endDateselectday:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(3) > div > div > div:nth-of-type(3) > div > div > div > section > div > div:nth-of-type(5) > a:nth-of-type(6)')

    },
    submitquery : function () {
        return select('div#app > div > div > div > section > div > section > button')


    },
    select_plant :function () {
        return select('div#app > div > div > div > section > div:nth-of-type(2) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(4) > a')

    },
    create_PO : function () {
        return select('div#app > div > div > div > section > div:nth-of-type(3) > div > section > button')

    } ,
    notfication : function () {
        return select('html > body > div:nth-of-type(3) > div > div > button')

},
    approve_PO : function () {
        return select('div#app > div > div > table > tbody > tr > td:nth-of-type(7) > a:nth-of-type(2)')
    },
    reject_PO : function () {
        return select('div#app > div > div > table > tbody > tr > td:nth-of-type(7) > a:nth-of-type(3)')
    },
    status_PO: function (){
        return select('div#app > div > div > table > tbody > tr > td:nth-of-type(7) > a')
    },
    view_details_status: function(){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(4) > a:nth-of-type(3)')
    },
    modify : function (){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(7) > a:nth-of-type(4)')
    },
    pending : function (){
        return select ('div#app > table > tbody > tr:nth-of-type(2) > td:nth-of-type(3) > span')
    },
    name_POs: function(){
        return select ('div#app > table > tbody > tr:nth-of-type(2) > td')
    },
    approve_Invoice: function(){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(7) > a:nth-of-type(2)')
    },

    extend: function(){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(7) > a:nth-of-type(2)')
    },
    submitEx: function(){
        return select('div#app > html > body > div:nth-of-type(2) > div:nth-of-type(2) > div > section > div > section > a')
    },
    recectEx: function(){
        return select('div#app > div > div > table > tbody > tr > td:nth-of-type(5) > a:nth-of-type(2)')
    },
    acceptEx: function(){
        return select('div#app > div > div > table > tbody > tr > td:nth-of-type(5) > a:nth-of-type(1)')
    },
   
   
   
};
