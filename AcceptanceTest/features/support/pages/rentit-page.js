const {Selector} = require('testcafe');

// Selectors

function select(selector) {
    return Selector(selector).with({boundTestRun: testController});
}

exports.rentit= {
    url: function() {
        return 'http://localhost:8080/#/login';
    },
    urlPlantOrder: function() {
        return 'http://localhost:8080/#/plantorder';
    },
    urlDispatch: function() {
        return 'http://localhost:8080/#/plantlisting';
    },
    login_text: function() {
        return select('.input')},
    login_pass: function() {
        return select("input[type='password']")},
    loginButton: function () {

        return select('.button.is-info.is-fullwidth')
    },
    mini: function () {
        return select('.input')

    },

    startDate:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(2) > div > div > div > div > input')

    },
    startDateselectday:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(2) > div > div > div:nth-of-type(3) > div > div > div > section > div > div:nth-of-type(2) > a:nth-of-type(4)')

    },

    endDate:function () {
        return select("input[type='.modal-card-body']")

    },
    endDateselectday:function () {
        return select('div#app > div > div > div > section > div > section > div:nth-of-type(3) > div > div > div:nth-of-type(3) > div > div > div > section > div > div:nth-of-type(3) > a:nth-of-type(6)')

    },
    submitquery : function () {
        return select('button.is-primary.is-outlined')


    },
    select_plant : function () {
        return select('div#app > div > div > div > section > div:nth-of-type(2) > table > tbody > tr > td:nth-of-type(4) > a')


    },
    create_PO : function () {
        return select('div#app > div > div > div > section > div:nth-of-type(3) > div > section > button')


    },
    details : function () {
         return select('div#app > div > div > table > tbody > tr > td > a')

    },
    accept: function (){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(5) > a')
    },
    reject : function (){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(5) > a:nth-of-type(2)')
    },
    dispatch : function (){
        return select ('div#app > table > tbody > tr > td:nth-of-type(4) > tr:nth-of-type(2) > a')
    },
    Invoice_PO : function (){
        return select ('div#app > div > div > table > tbody > tr > td:nth-of-type(5) > a:nth-of-type(1)')
    }


};
